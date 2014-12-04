/*
C14 : $16
C15 : $17
C16 : $18
C17 : $19
C18 : $20
C19 : $21
C20 : $22
C21 : $23
C1 : $3
*/
data = LOAD 'D:/Kaggle/AvazuCtrPrediction/train/' USING PigStorage(',');

distinctValsC14 = DISTINCT data$16;

STORE distinctValsC14 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C14.txt' USING PigStorage(',');

distinctValsC15 = DISTINCT data$17;

STORE distinctValsC15 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C15.txt' USING PigStorage(',');

distinctValsC16 = DISTINCT data$18;

STORE distinctValsC16 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C16.txt' USING PigStorage(',');

distinctValsC17 = DISTINCT data$19;

STORE distinctValsC17 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C17.txt' USING PigStorage(',');

distinctValsC18 = DISTINCT data$20;

STORE distinctValsC18 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C18.txt' USING PigStorage(',');

distinctValsC19 = DISTINCT data$21;

STORE distinctValsC19 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C19.txt' USING PigStorage(',');

distinctValsC20 = DISTINCT data$22;

STORE distinctValsC20 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C20.txt' USING PigStorage(',');

distinctValsC21 = DISTINCT data$23;

STORE distinctValsC21 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C21.txt' USING PigStorage(',');

distinctValsC1 = DISTINCT data$3;

STORE distinctValsC1 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C1.txt' USING PigStorage(',');



