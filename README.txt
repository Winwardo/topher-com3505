Topher Winward's (120134353) COM3505 submission.
crwinward1@sheffield.ac.uk
Robot Waiter.
This entire assignment, unless explicitly stated, is my own work.
Some segments are sourced from Steve Maddock, inspired by websites (URLs are provided closer to the code), and some images are collected from Google Images.

# How to run
Several tools are provided for convenient compilation and running.
 - clean.bat: Deletes the /bin/ folder
 - build.bat: Recursively compiles all .java files in the /src/ folder. This can take quite a while.
 - run.bat:   Runs the /src/main/Assignment.class class, main.Assignment, with the classpath set to include the local jogl jar files.
 - clean.build.run.bat: Runs all three above bat files in order.

It is recommended to run run.bat before cleaning and recompiling to see that it works, as compiling can take a very long time.

This assumes that the appropriate JOGL .dll files are in the PATH environment path. If not, extract the DLLs directly to this folder (topher-com3505). Make sure the DLLs are correct for the given PC - for example, PCs in The Diamond may require the windows-i586 binary dlls, whereas the Lewin computers can use the dlls provided by Steve Maddock on the teaching website.

Should you be unable to compile, please look in the /packed/ folder for topher.jar. This is a precompiled runnable .jar with all JOGL jars included.

# Advanced option
The advanced option I have undertaken is "You are the robot". The robot's view can be seen displayed on a TV at the end of the room. You can also click the "Robot view camera" button in the bottom left of the GUI to access its vision.

# How to use the program
Buttons are provided at the top left for starting, stopping, and restarting animation. Restarting will take place on the next frame, that is restarting while paused won't show an effect until the animation is unpaused. Both the robot's body and its right arm have keyframe animations, all tilting and "looking around" is physically simulated for realism.

At the bottom is 5 check boxes for various lighting settings. The development "World lights" option is off by default. Each spotlight, and the robot's light may also be toggled. Note how the texture on a light (including the robot's light) changes depending on whether it is on or off.
The "Enable fake radiosity" button allows toggling of extra point lights just in front of a spotlight to give the illusion that the light is bouncing back off the surface it's pointing at. This increases the realism of the lighting, however incase it goes against the idea of being "dramatic, like someone shining a torch in a darkened room" I left in an option to disable it.

The top of the screen has buttons for various shader configurations. The two most important are Blinn-Phong, which computes a per-pixel ambient/diffuse/specular value, and "No shader" which reenables the fixed-function pipeline, where all lighting is computed per vertex. In "No shader" mode, note how you can still see the shape of the spotlight as the walls are made up of many vertices instead of singular large planes.

You can drag the mouse around, with left click to move the camera, and right click to zoom. Zooming in past the mid-point will reverse the camera, so look out for this.

On the left there is also a visual representation of the main scenegraph. By clicking the little blue button on the left of the folder icon, it will expand showing you all its children. Selecting any folder or node will "select" that object in the 3D world, drawing axes to mark its location. For an easy to find example, open the tree two layers, and then select the bottom SceneGraphNode. This is the Robot's lights, and will render the axes just infront of its chest.

# Other notes
This assignment makes use of display lights, particularly for all planes and cuboids, to speed up draw time.
A single frame buffer object is used and drawn to for display on the TV at the end of the screen. Due to draw depth limits, the TV can only render itself twice, and shows a black screen inside itself any deeper.

The /res/ folder holds all textures and shader code. Some textures are ultimately unused, but are still loaded by the program, and as such are left in the folder. All photos were sourced from a Google Image search, and as such the images may not be publically usable. However as this is a non-commericial private assignment, this should be fine. The textures can be updated with new, open source ones if required.

The screenshots folder shows how the radiosity bounce affects lighting in the room, as well as shows a photo of the "Edit Scene" which was used throughout development to build up objects. It had three lights and rotated the object to show off detail and specular. This was removed in the final version and codebase as it was not needed, but is still available in earlier git versions.

Three 30 second videos have been captured and placed on Youtube (unlisted) for viewing. Any stuttering and artifacts are a result of the FRAPS software used to record it, and not the assignment itself.
https://www.youtube.com/watch?v=z4fAfZG0tPA
https://www.youtube.com/watch?v=5EpMfCzTBnU
https://www.youtube.com/watch?v=RYX4grkSQYM