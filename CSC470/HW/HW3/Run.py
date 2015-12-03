# File name: Run.py
# Author: Jeff Registre
# Date created: 10/06/2015

#Begin code

#Imports
import webbrowser
import os

vertPath = os.path.relpath("Shaders/vert.glsl")
fragPath = os.path.relpath("Shaders/frag.glsl")
baseHtmlPath = os.path.relpath("HTML/page.html")
outFilePath  = os.path.relpath("HTML/program.html")

firstStop  = "id=\"shader-vs\""
secondStop = "id=\"shader-fs\""

def main():
    compiled = open(outFilePath, "w")
    with open(baseHtmlPath) as f:
        for line in f:
            if line.find(firstStop) is not -1:
                appendVert(compiled, line)
            elif line.find(secondStop) is not -1:
                appendFrag(compiled, line)
            else:
                compiled.write(line)
    compiled.close()
    webbrowser.open_new_tab(outFilePath)
    com = raw_input("Enter \"Compile\" to save file or anything else to delete and quit: ")
    if com.find("compile") is -1:
        os.remove(outFilePath)
                
        
def appendVert(file, lastLine):
    file.write(lastLine)
    with open(vertPath) as vf:
        for line in vf:
            if line.find("#") is -1:
                file.write(line)
                
                
def appendFrag(file, lastLine):
    file.write(lastLine)
    with open(fragPath) as fF:
        for line in fF:
            if line.find('#') is -1:
                file.write(line)
    
main()