
"D:/work/tools/maven/default\bin\mvn" deploy:deploy-file -DgroupId=cn.net.vidyo -DartifactId=dylink-data   -Dversion=1.0.0.5.RELEASE -Dpackaging=jar -Dfile=D:\work\projects\dylink\dylink\dylink-common\dylink-data\target\dylink-data-1.0.0.5.RELEASE.jar     -Durl=http://maven.vdyoo.cn/repository/maven-releases/ -DrepositoryId=vdyoo

pause

"D:/work/tools/maven/default\bin\mvn" deploy:deploy-file -DgroupId=cn.net.vidyo -DartifactId=dylink-common -Dversion=1.0.0.5.RELEASE -Dpackaging=jar -Dfile=D:\work\projects\dylink\dylink\dylink-common\dylink-common\target\dylink-common-1.0.0.5.RELEASE.jar -Durl=http://maven.vdyoo.cn/repository/maven-releases/ -DrepositoryId=vdyoo
