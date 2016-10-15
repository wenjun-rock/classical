prev=`pwd`
base=$prev
lib1=`cd $base/../classical-mozart/target/ && pwd`
lib2=`cd $base/../classical-mozart/target/lib && pwd`
cd $prev
echo $lib1
echo $lib2
cp1=`ls $lib1/*.jar | xargs | sed "s/ /:/g"`
cp2=`ls $lib2/*.jar | xargs | sed "s/ /:/g"`
cp=$base/conf:$cp1:$cp2
echo $cp

if [ -z "$1"] 
then 
cn='fwj.classical.mozart.MozartLaunch'
else 
cn=$1
fi

nohup java -cp $cp $cn >> mozart-log/mozart.log 2>&1 &