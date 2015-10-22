for /r %%a in (*.java) do ( javac -cp ".;src;lib/gluegen.jar;lib/jogl-all.jar" -d "bin" "%%a" )
pause