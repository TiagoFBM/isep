REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.app.spm\target\base.app.spm-1.3.0-SNAPSHOT.jar;base.app.spm\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% fabrica.spm.BaseSPM
