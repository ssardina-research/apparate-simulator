
## Configuration File

This is the file configuring how the application should run. It is mostly self-explanatory:


```
map=maps/blastedlands.map           % the map file, else it would use a blank map
grid=euclidean                      % either manhatten or euclidean
totaltime=10000                     % total time allowed in milliseconds (0 =  no limit)
pulsertime=200                      % pause time between pulses (only if not quiet)
mintimestep=1                       % step of less than 1ms are not counted towards time
steps=200                           % how many total steps allowed (0 = no limit)
steptime=400                        % time allowed in each step (default: no limit)
startpos=200,110                    % start position (missing or =random --> random pick)
destpos=300,270                     % goal position (missing or =random --> random pick)
quiet=false                         % no GUI, print out stats (arrived?, #steps, #distance, #time)
killonlimit=true                    % kill the agent if limit time exceeded?
managegc=falsE                      % Performs garbage collection before each step
#script=maps/testscript.mapscript   % the file with the scripted events

# Location of the agent JAR or binary
agentname=au.edu.rmit.agent.MyAgent # Fully qualified class of agent to be used
agentloc=agent/213/MyAgenSystem.jar # Directory where the agentname class is located

### Which agents to make available?
agentclass=au.edu.rmit.agtgrp.apparate.agents.MyRandomAgent

```