package util;

import java.util.ArrayList;
import java.util.List;

import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_prob;

import data.Elve;
import data.Toy;

public class LinearProgrammingHelper {

	/*
	 * min e1+-e2-e3... + a11*t1+a12*t2+a13*t3+... 
	 * Contstraints :
	 * 
	 * Elves Constraints
	 * a11*t1+a12*t2+a13*t3... >= a21*t1+a22*t2+a23*t3...
	 * a11*t1+a12*t2+a13*t3... >= a31*t1+a32*t2+a33*t3...
	 * 
	 * Toys Constraints
	 * a11+a21+a31...  = 1
	 * a12+a22+a32...  = 1
	 * a13+a23+a33...  = 1
	 */
	public List<Toy>[] optimize(Elve[] elves, ArrayList<Toy> toys,
			int startIndex, int endIndex) {

		List<Toy>[] assignments;

		glp_prob lp = GLPK.glp_create_prob();
		GLPK.glp_set_prob_name(lp, "lp_" + startIndex + "_" + endIndex);

		int maxTimeElveId = getHishestEndTimeElveIndex(elves);

		int noOfToys = endIndex - startIndex + 1;
		int noOfElves = elves.length - 1;
		int totalCols = (noOfElves) * (noOfToys + 1);

		GLPK.glp_add_cols(lp, totalCols);

		for (int i = 1; i <= totalCols; i++) {
			GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_BV);

			if (i <= noOfElves) {
				GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_FX, 1, 1);
			}
		}

		int numOfIndicesElvesConstraint = noOfToys * 2 + 2;
		SWIGTYPE_p_int constraintsIndices = GLPK
				.new_intArray(numOfIndicesElvesConstraint);
		SWIGTYPE_p_double constraintsVals = GLPK
				.new_doubleArray(numOfIndicesElvesConstraint);
		GLPK.intArray_setitem(constraintsIndices, 1, maxTimeElveId);
		GLPK.doubleArray_setitem(constraintsVals, 1,
				elves[maxTimeElveId].getLastJobFinishTime());
		int startOfMaxTimeElveIdAssignments = noOfElves + maxTimeElveId
				* noOfToys;
		for (int i = 0; i < noOfToys; i++) {
			GLPK.intArray_setitem(constraintsIndices, i + 3,
					startOfMaxTimeElveIdAssignments + i);
			GLPK.doubleArray_setitem(constraintsVals, i + 3, toys.get(i)
					.getIdealEndTime());
		}

		int contraintCount = 0;
		for (int elveId = 1; elveId <= noOfElves; elveId++) {
			if (elveId != maxTimeElveId) {
				contraintCount++;
				GLPK.glp_add_rows(lp, 1);
				GLPK.glp_set_row_bnds(lp, contraintCount, GLPKConstants.GLP_LO,
						0, 0);

				GLPK.intArray_setitem(constraintsIndices, 2, elveId);
				GLPK.doubleArray_setitem(constraintsVals, 2, -1
						* elves[elveId].getLastJobFinishTime());

				int startElveAssignments = noOfElves + elveId * noOfToys;

				for (int i = 0; i < noOfToys; i++) {
					GLPK.intArray_setitem(constraintsIndices, 2 + noOfToys
							+ i + 1, startElveAssignments + i);
					GLPK.doubleArray_setitem(constraintsVals, 2 + noOfToys
							+ i + 1, -1 * toys.get(i).getIdealEndTime());
				}

				GLPK.glp_set_mat_row(lp, contraintCount,
						numOfIndicesElvesConstraint, constraintsIndices,
						constraintsVals);

			}
		}
		
		
		for(int i=1;i<=noOfToys;i++)
		{
			contraintCount++;
			GLPK.glp_add_rows(lp, 1);
			GLPK.glp_set_row_bnds(lp, contraintCount, GLPKConstants.GLP_FX,
					1, 1);
			for(int j=1;j<=noOfElves;j++)
			{
				GLPK.intArray_setitem(constraintsIndices, j, noOfElves + ((j-1)*noOfToys)+i);
				GLPK.doubleArray_setitem(constraintsVals, j, 1);
			}
			
			GLPK.glp_set_mat_row(lp, contraintCount,
					noOfElves, constraintsIndices,
					constraintsVals);
		}
		

		return assignments;
	}

	private int getHishestEndTimeElveIndex(Elve[] elves) {
		int maxEndTime = elves[1].getLastJobFinishTime();
		int maxEndTimeId = 1;
		for (int i = 2; i < 901; i++) {
			if (elves[i].getLastJobFinishTime() > maxEndTime) {
				maxEndTime = elves[i].getLastJobFinishTime();
				maxEndTimeId = i;
			}
		}

		return maxEndTimeId;
	}

}
