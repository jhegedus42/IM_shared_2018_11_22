date=`date`
cd /Users/joco/dev/scala.js/react/sjs-playaround/simpler_sbt/app
githash=`git log | head -n 1`
hash=`python -c 'import sys,uuid; sys.stdout.write(uuid.uuid4().hex)'`
echo "$hash $githash $date" | pbcopy && pbpaste && echo
