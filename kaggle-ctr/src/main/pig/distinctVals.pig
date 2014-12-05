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
banner_pos : $4
site_id : $5
site_domain : $6
site_category : $7
app_id : $8
app_domain : $9
app_category : $10
device_id : $11
device_ip : $12
device_model : $13
device_type : $14
device_conn_type : $15
*/
data = LOAD 'D:/Kaggle/AvazuCtrPrediction/train/' USING PigStorage(',');

distinctValsC14 = FOREACH data GENERATE $16;
distinctValsC14 = DISTINCT distinctValsC14;

STORE distinctValsC14 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C14.txt' USING PigStorage(',');

distinctValsC15 = FOREACH data GENERATE $17;
distinctValsC15 = DISTINCT distinctValsC15;

STORE distinctValsC15 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C15.txt' USING PigStorage(',');

distinctValsC16 = FOREACH data GENERATE $18;
distinctValsC16 = DISTINCT distinctValsC16;

STORE distinctValsC16 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C16.txt' USING PigStorage(',');

distinctValsC17 = FOREACH data GENERATE $19;
distinctValsC17 = DISTINCT distinctValsC17;

STORE distinctValsC17 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C17.txt' USING PigStorage(',');

distinctValsC18 = FOREACH data GENERATE $20;
distinctValsC18 = DISTINCT distinctValsC18;

STORE distinctValsC18 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C18.txt' USING PigStorage(',');

distinctValsC19 = FOREACH data GENERATE $21;
distinctValsC19 = DISTINCT distinctValsC19;

STORE distinctValsC19 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C19.txt' USING PigStorage(',');

distinctValsC20 = FOREACH data GENERATE $22;
distinctValsC20 = DISTINCT distinctValsC20;

STORE distinctValsC20 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C20.txt' USING PigStorage(',');

distinctValsC21 = FOREACH data GENERATE $23;
distinctValsC21 = DISTINCT distinctValsC21;

STORE distinctValsC21 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C21.txt' USING PigStorage(',');

distinctValsC1 = FOREACH data GENERATE $3;
distinctValsC1 = DISTINCT distinctValsC1;

STORE distinctValsC1 INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/C1.txt' USING PigStorage(',');

distinctValsBannerPos = FOREACH data GENERATE $4;
distinctValsBannerPos = DISTINCT distinctValsBannerPos;

STORE distinctValsBannerPos INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/banner_pos.txt' USING PigStorage(',');

distinctValsSiteId = FOREACH data GENERATE $5;
distinctValsSiteId = DISTINCT distinctValsSiteId;


STORE distinctValsSiteId INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/site_id.txt' USING PigStorage(',');

distinctValsSiteDomain = FOREACH data GENERATE $6;
distinctValsSiteDomain = DISTINCT distinctValsSiteDomain;

STORE distinctValsSiteDomain INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/site_domain.txt' USING PigStorage(',');

distinctValsSiteCategory = FOREACH data GENERATE $7;
distinctValsSiteCategory = DISTINCT distinctValsSiteCategory;

STORE distinctValsSiteCategory INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/site_category.txt' USING PigStorage(',');

distinctValsAppId = FOREACH data GENERATE $8;
distinctValsAppId = DISTINCT distinctValsAppId;

STORE distinctValsAppId INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/app_id.txt' USING PigStorage(',');

distinctValsAppDomain = FOREACH data GENERATE $9;
distinctValsAppDomain = DISTINCT distinctValsAppDomain;

STORE distinctValsAppDomain INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/app_domain.txt' USING PigStorage(',');

distinctValsAppCategory = FOREACH data GENERATE $10;
distinctValsAppCategory = DISTINCT distinctValsAppCategory;

STORE distinctValsAppCategory INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/app_category.txt' USING PigStorage(',');

distinctValsDeviceId = FOREACH data GENERATE $11;
distinctValsDeviceId = DISTINCT distinctValsDeviceId;

STORE distinctValsDeviceId INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/device_id.txt' USING PigStorage(',');

distinctValsDeviceIp = FOREACH data GENERATE $12;
distinctValsDeviceIp = DISTINCT distinctValsDeviceIp;

STORE distinctValsDeviceIp INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/device_ip.txt' USING PigStorage(',');

distinctValsDeviceModel = FOREACH data GENERATE $13;
distinctValsDeviceModel = DISTINCT distinctValsDeviceModel;

STORE distinctValsDeviceModel INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/device_model.txt' USING PigStorage(',');

distinctValsDeviceType = FOREACH data GENERATE $14;
distinctValsDeviceType = DISTINCT distinctValsDeviceType;

STORE distinctValsDeviceType INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/device_type.txt' USING PigStorage(',');

distinctValsDeviceConnType = FOREACH data GENERATE $15;
distinctValsDeviceConnType = DISTINCT distinctValsDeviceConnType;

STORE distinctValsDeviceConnType INTO 'D:/Kaggle/AvazuCtrPrediction/distinctTrainVals/device_conn_type.txt' USING PigStorage(',');


