package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_iocp;
import org.gnu.glpk.glp_prob;

import data.Elve;
import data.Toy;

public class LinearProgrammingHelper {

	static {
		System.out.println("Loading libraries...");
		System.out.println(System.getProperty("java.library.path"));
		/*
		 * String prop = System.getProperty("java.library.path");
		 * System.setProperty("java.library.path", "D:\\glpk-4.55\\w32;" +
		 * prop);
		 * 
		 * try { final Field sysPathsField = ClassLoader.class
		 * .getDeclaredField("sys_paths"); sysPathsField.setAccessible(true);
		 * sysPathsField.set(null, null); } catch (IllegalArgumentException e) {
		 * 
		 * e.printStackTrace(); throw new RuntimeException(e); } catch
		 * (IllegalAccessException e) {
		 * 
		 * e.printStackTrace(); throw new RuntimeException(e); } catch
		 * (NoSuchFieldException e) { e.printStackTrace(); throw new
		 * RuntimeException(e); } catch (SecurityException e) {
		 * e.printStackTrace(); throw new RuntimeException(e); }
		 */
	}

	/*
	 * min e1+a11*t1+a12*t2+a13*t3+... Contstraints :
	 * 
	 * Elves Constraints a11*t1+a12*t2+a13*t3... >= a21*t1+a22*t2+a23*t3...
	 * a11*t1+a12*t2+a13*t3... >= a31*t1+a32*t2+a33*t3...
	 * 
	 * Toys Constraints a11+a21+a31... = 1 a12+a22+a32... = 1 a13+a23+a33... = 1
	 * 
	 * where aij = 1 if toy j is assigned to elve i ti = ideal finish time for
	 * toy i
	 * 
	 * Above we have assumed that elve finishing last toy is 1st elve which is
	 * not the case in code.
	 */
	public List<Toy>[] optimize(Elve[] elves, ArrayList<Toy> toys,
			int startIndex, int endIndex) {
		List<Toy>[] assignments = null;
		if (endIndex >= toys.size()) {
			endIndex = toys.size() - 1;
		}
		int noOfToys = endIndex - startIndex + 1;
		int noOfElves = elves.length - 1;
		int totalCols = (noOfElves) * (noOfToys + 1);
		int numOfIndicesElvesConstraint = noOfToys * 2 + 2;
		SWIGTYPE_p_int constraintsIndices = GLPK
				.new_intArray(numOfIndicesElvesConstraint);
		SWIGTYPE_p_double constraintsVals = GLPK
				.new_doubleArray(numOfIndicesElvesConstraint);
		glp_prob lp = GLPK.glp_create_prob();
		glp_iocp parmIocp = new glp_iocp();
		GLPK.glp_init_iocp(parmIocp);
		//parmIocp.setBr_tech(value)
		//parmIocp.set
		//parmIocp.setCov_cuts(GLPKConstants.GLP_ON);
		parmIocp.setGmi_cuts(GLPKConstants.GLP_ON);
		parmIocp.setPresolve(GLPKConstants.GLP_ON);
		parmIocp.setTm_lim(3 * 60 * 60 * 1000);

		try {

			GLPK.glp_set_prob_name(lp, "lp_" + startIndex + "_" + endIndex);

			int maxTimeElveId = getHishestEndTimeElveIndex(elves);

			GLPK.glp_add_cols(lp, totalCols);

			for (int i = 1; i <= totalCols; i++) {
				GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_BV);

				if (i <= noOfElves) {
					GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_FX, 1, 1);
				}
			}

			GLPK.intArray_setitem(constraintsIndices, 1, maxTimeElveId);
			GLPK.doubleArray_setitem(constraintsVals, 1,
					elves[maxTimeElveId].getLastJobFinishTime());
			int beforeStartOfMaxTimeElveIdAssignments = noOfElves
					+ (maxTimeElveId - 1) * noOfToys;
			for (int i = 1; i <= noOfToys; i++) {
				GLPK.intArray_setitem(constraintsIndices, i + 2,
						beforeStartOfMaxTimeElveIdAssignments + i);
				GLPK.doubleArray_setitem(constraintsVals, i + 2,
						getToyWeight(toys.get(startIndex + i - 1)));
			}

			int contraintCount = 0;
			for (int elveId = 1; elveId <= noOfElves; elveId++) {
				if (elveId != maxTimeElveId) {
					contraintCount++;
					GLPK.glp_add_rows(lp, 1);
					GLPK.glp_set_row_bnds(lp, contraintCount,
							GLPKConstants.GLP_LO, 0, 0);

					GLPK.intArray_setitem(constraintsIndices, 2, elveId);
					GLPK.doubleArray_setitem(constraintsVals, 2, -1
							* elves[elveId].getLastJobFinishTime());

					int beforeStartElveAssignments = noOfElves + (elveId - 1)
							* noOfToys;

					for (int i = 1; i <= noOfToys; i++) {
						GLPK.intArray_setitem(constraintsIndices, 2 + noOfToys
								+ i, beforeStartElveAssignments + i);
						GLPK.doubleArray_setitem(constraintsVals, 2 + noOfToys
								+ i,
								-1 * getToyWeight(toys.get(startIndex + i - 1)));
					}

					GLPK.glp_set_mat_row(lp, contraintCount,
							numOfIndicesElvesConstraint, constraintsIndices,
							constraintsVals);

				}
			}

			for (int i = 1; i <= noOfToys; i++) {
				contraintCount++;
				GLPK.glp_add_rows(lp, 1);
				GLPK.glp_set_row_bnds(lp, contraintCount, GLPKConstants.GLP_FX,
						1, 1);
				for (int j = 1; j <= noOfElves; j++) {
					GLPK.intArray_setitem(constraintsIndices, j, noOfElves
							+ ((j - 1) * noOfToys) + i);
					GLPK.doubleArray_setitem(constraintsVals, j, 1);
				}

				GLPK.glp_set_mat_row(lp, contraintCount, noOfElves,
						constraintsIndices, constraintsVals);
			}

			GLPK.glp_set_obj_coef(lp, maxTimeElveId,
					elves[maxTimeElveId].getLastJobFinishTime());

			for (int i = 1; i <= noOfToys; i++) {
				GLPK.glp_set_obj_coef(lp, beforeStartOfMaxTimeElveIdAssignments
						+ i, getToyWeight(toys.get(startIndex + i - 1)));
			}

			GLPK.glp_set_obj_name(lp, "obj");
			GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MIN);
			GLPK.glp_set_obj_coef(lp, 0, 0);

			int status = GLPK.glp_intopt(lp, parmIocp);

			if (status != 0) {
				throw new RuntimeException("Problem solving GLPK , error : "
						+ status);
			}

			assignments = new List[noOfElves + 1];

			for (int i = 1; i < noOfElves + 1; i++) {
				int beforeStartOfAssignments = noOfElves + (i - 1) * noOfToys;
				List<Toy> elveAssignments = new ArrayList<Toy>();
				for (int j = 1; j <= noOfToys; j++) {
					double val = GLPK.glp_mip_col_val(lp,
							beforeStartOfAssignments + j);
					if (val == 1) {
						elveAssignments.add(toys.get(startIndex + j - 1));
					}
				}

				Collections.sort(elveAssignments);
				assignments[i] = elveAssignments;
			}

		} finally {

			GLPK.delete_intArray(constraintsIndices);
			GLPK.delete_doubleArray(constraintsVals);
			lp.delete();
			parmIocp.delete();

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

	private double getToyWeight(Toy toy) {
		int idealStartTime = toy.getIdealStartTime();
		int idealEndTime = toy.getIdealEndTime();
		int sanctionedTime = TimeHelper.getSanctionedMinsForRange(
				idealStartTime, idealEndTime);
		int unsanctionedTime = (idealEndTime - idealStartTime) - sanctionedTime;
		return (toy.getIdealEndTime())
				/ (Math.pow(1.02, sanctionedTime) * Math.pow(0.9,
						unsanctionedTime));
	}

}
