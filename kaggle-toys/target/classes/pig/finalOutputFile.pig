data = LOAD '$inputFile' USING PigStorage(',') AS (toyId:chararray,elveId:chararray,startTimeStr:chararray,duration:int,endTime:int);
orderedData = ORDER data BY endTime;
finalData = FOREACH orderedData GENERATE toyId,elveId,startTimeStr,duration;
STORE finalData INTO '$outputFile' USING PigStorage(',');