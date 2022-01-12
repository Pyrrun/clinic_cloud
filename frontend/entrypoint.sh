#!/bin/sh

if [ ! -z $VUE_APP_BASE_URL ]; then
ROOT_DIR=/app

echo "Replacing env constants in JS"
for file in $ROOT_DIR/js/app.*.js* $ROOT_DIR/index.html $ROOT_DIR/precache-manifest*.js;
do
  echo "Processing $file ...";
  sed -i 's|VUE_APP_BASE_URL|'${VUE_APP_BASE_URL}'|g' $file

done
fi
exec $@
