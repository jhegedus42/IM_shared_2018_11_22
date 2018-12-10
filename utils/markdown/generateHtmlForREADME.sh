rm README.html

pandoc README.md -f gfm+backtick_code_blocks+fenced_code_blocks  --number-sections    --css utils/markdown/pandoc.css -t html5 -s -o README.html  --metadata pagetitle="README"  


