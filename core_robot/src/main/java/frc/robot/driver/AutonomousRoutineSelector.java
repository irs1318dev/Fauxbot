package frc.robot.driver;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.LoggingKey;
import frc.robot.common.LoggingManager;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.*;
import frc.robot.driver.controltasks.*;

@Singleton
public class AutonomousRoutineSelector
{
    private final ILogger logger;

    private final PathManager pathManager;
    private final IDriverStation driverStation;

    private final ISendableChooser<StartPosition> positionChooser;
    private final ISendableChooser<AutoRoutine> routineChooser;

    public enum StartPosition
    {
        Center,
        Left,
        Right
    }

    public enum AutoRoutine
    {
        None,
    }

    /**
     * Initializes a new AutonomousRoutineSelector
     */
    @Inject
    public AutonomousRoutineSelector(
        LoggingManager logger,
        PathManager pathManager, 
        IRobotProvider provider)
    {
        // initialize robot parts that are used to select autonomous routine (e.g. dipswitches) here...
        this.logger = logger;
        this.pathManager = pathManager;

        this.driverStation = provider.getDriverStation();

        INetworkTableProvider networkTableProvider = provider.getNetworkTableProvider();

        this.routineChooser = networkTableProvider.getSendableChooser();
        this.routineChooser.addDefault("None", AutoRoutine.None);
        networkTableProvider.addChooser("Auto Routine", this.routineChooser);

        this.positionChooser = networkTableProvider.getSendableChooser();
        this.positionChooser.addDefault("center", StartPosition.Center);
        this.positionChooser.addObject("left", StartPosition.Left);
        this.positionChooser.addObject("right", StartPosition.Right);
        networkTableProvider.addChooser("Start Position", this.positionChooser);

        RoadRunnerTrajectoryGenerator.generateTrajectories(this.pathManager);
    }

    /**
     * Check what routine we want to use and return it
     * @param mode that is starting
     * @return autonomous routine to execute during autonomous mode
     */
    public IControlTask selectRoutine(RobotMode mode)
    {
        String driverStationMessage = this.driverStation.getGameSpecificMessage();
        this.logger.logString(LoggingKey.AutonomousDSMessage, driverStationMessage);
        if (mode == RobotMode.Test)
        {
            return AutonomousRoutineSelector.GetFillerRoutine();
        }

        if (mode != RobotMode.Autonomous)
        {
            return null;
        }

        StartPosition startPosition = this.positionChooser.getSelected();
        if (startPosition == null)
        {
            startPosition = StartPosition.Center;
        }

        AutoRoutine routine = this.routineChooser.getSelected();
        if (routine == null)
        {
            routine = AutoRoutine.None;
        }

        this.logger.logString(LoggingKey.AutonomousSelection, startPosition.toString() + "." + routine.toString());

        return AutonomousRoutineSelector.GetFillerRoutine();
    }

    /**
     * Gets an autonomous routine that does nothing
     */
    private static IControlTask GetFillerRoutine()
    {
        return new WaitTask(0.0);
    }
} // yaaaaaAAAaaaAaaaAAAAaa








































































































































/*
                                      .                                                             
                                    .;+;+                                                           
                                    .+;;'   `,+'.                                                   
                                    ;';;+:..`` :+'+                                                 
                                    ,'+`    .+;;;;;+                                                
                                     ;,,, .+;;;;;'+++;                                              
                                     ;' `+;;;;;#+'+'+''#:.                                          
                                     '`+';;;'+;+;+++'''+'.                                          
                                     #';;;;#';+'+'''+''+'                                           
                                     ;;;;#;,+;;+;;;'''''':                                          
                                     ';'++'.`+;;'';;''+'',                                          
                                     :#'#+'``.'+++'#++'':`                                          
                                      `';++##```##+.''.##                                           
                                      +++#   #`#  `++++                                             
                                      +'#+ # :#: # ##'+                                             
                                      `#+#   +`+   #'#`                                             
                                       :,.+,+,`:+,+..,                                              
                                       `,:```,`,`.`;,                                               
                                        :+.;``.``;.#;                                               
                                        .'``'+'+'``'.                                               
                                         ,````````..                                                
                                          :```````:                                                 
                                          +``.:,``'                                                 
                                          :```````:                                                 
                                           +`````+                                                  
                                            ';+##                                                   
                                            '```'                                                   
                                           `'```'`                                                  
                                         .+''''''''                                                 
                                        +;;;;;;;;''#                                                
                                       :       `   `:                                               
                                      `,            '                                               
                                      +              '                                              
                                     ,;';,``.``.,,,:;#                                              
                                     +;;;;;;;;;;;;;;;'                                              
                                    ,';;;;;;;;;;;;;;;',                                             
                                    +:;;;;;;';;;;;;;;;+                                             
                                   `.   .:,;+;;:::;.``,                                             
                                   :`       #,       `.`                                            
                                   +       # ;        .;                                            
                                  .;;,`    ,         `,+                                            
                                  +;;;;;;''';;;;;;;';;';                                            
                                  +;;;;;;;';;;;;;;;;;'';;                                           
                                 `';;;;;;';;;;;;;;;;;';;+                                           
                                 + `:;;;;+;;;;;;;;';'''::                                           
                                 '     `:  ```````    ,  ,                                          
                                :       '             ;  +                                          
                                '`     ..             ,  ,                                          
                               ,;;;;;..+,`        ```.':;',                                         
                               +;;;;;;'+;;;;;;;;;;;;;;+;;;+                                         
                               ';;;;;;++;;;;;;;;;;;;;;';;;+                                         
                              `.:';;;;;#;;;;;;;;;;;;;;';;;;`                                        
                              ;    `,; ',:;;';;';;;;;:;``  +                                        
                              +      ; ;              ;    `                                        
                              ;      : +              '    `;                                       
                              ';:`` `` '              :`,:;;+                                       
                             `';;;;'+  +,..```````..:;#;;;;;;.                                      
                             `;;;;;;+  +;;;;;;;;;;;;;':';;;;;#                                      
                             .;;;;;;+  ';;;;;;;;;;;;;;,';;;;` .                                     
                             : `.;;'+  +;;;;;;;;;;;;;','.`    +                                     
                             '      ;  +.,,;:;:;;;,..`: ,     ``                                    
                             +      ,  '              : ;   .;'+                                    
                             +.`   ``  +              ;  ;:;;;;':                                   
                             ';;;';;`  +             .'  ;;;;;;;+                                   
                             ';;;;;'   :+++#++##+#+''',   +;;;;.`.                                  
                             +;;;;;'   +;;::;;;+:+;;'',   ,;;.   +                                  
                            ``:;;;;+   +;;:;;;:+;+;;++;    +     .`                                 
                             `   ``'   +;;;;;;;+;+;;'+;     ,   ;#,                                 
                            .      ;   ';;;;;;;;;;;;++'     + .+``.;                                
                            ``     ;   ';;;;;;+;';;;'+'      #`````:,                               
                             +++;,:.   ':;''++;:';:;'';      +``````,`                              
                             ,```,+    +;;';:;;+;;;;'';      +``````,+                              
                            .``````:   ;:;;++';;;;;;';,      ,``:#``+`.                             
                            ,``````'   `';;;;:;;;;;;+;`     '+``+:'`..'                             
                            ,``````'    +;;;;;;;;;;;''     ;:'``#;;.`++                             
                            ```````;    `;:;;;;;;;;;;#     ':'``++:+`+;                             
                            ```'`.`;     +;;;;;;;;;;;+    :::#``' +#`';                             
                            ,``'`:`#     `';;;;;;;;;;+    +:'.`,. ++`;;                             
                            +`.``+`'     :#;;;;;;;;;;;`   +:# ,`  +;`.'                             
                           ,.`+`.:.      ##;;;;;;;;;;;'   ,'`     ;:+#                              
                           '`;.`+`#      ##+;;;;;;;;;;+          ,::;                               
                           ,+,`:``,     :###;;;;;;;;;:'          +:;`                               
                            '`,,`+      ';##';;;;;;;;;;.         +:#                                
                             '+.+       +;;##;;;;;;;;;;'         ;:;                                
                               `       :;;;+#;;;;;;;;;;+        ;::`                                
                                       +;;;;#+;;;;;;;;;;        +:'                                 
                                       ';;;;+#;;;;;;;;;;.       ;:'                                 
                                      ,;;;;;;#;;;;;;;;;;+      +::.                                 
                                      +;;;;;;'';;;;;;;;;'      +:+                                  
                                     `;;;;;;;;#;;;;;;;;;;`    `;:+                                  
                                     ,;;;;;;;;+;;;;;;;;;;+    ':;,                                  
                                     +;;;;;;;;;+;;;;;;;;;'    +:+                                   
                                    .;;;;;;;;;+,;;;;;;;;;;`   ;;+                                   
                                    ';;;;;;;;;, ';;;;;;:;;,  +;:,                                   
                                    ';;;;;;;;'  +;;;;;;;;;'  +:+                                    
                                   ;;;;;;;;;;+  ,;;;;;;;;;+  ;:'                                    
                                   +;;;;;;;;;    ';;;;;;;;;`;:;`                                    
                                   ;;;;;;;;;+    +;;;;;;;;;+#:+                                     
                                  ';;;;;;;;;:    ;;;;;;;;;;';:'                                     
                                 `';;;;;;;:'      ';;;;;;;;;;:.                                     
                                 .;;;;;;;;;+      +;;;;;;;;;'+                                      
                                 +;;;;;;;;;       ';;;;;;;;;#+                                      
                                `;;;;;;;;;+       `;;;;;;;;;;`                                      
                                +;;;;;;;;;.        +;;;;;;;;;`                                      
                                ';;;;;;;:'         ;;;;;;;;;;;                                      
                               :;;;;;;;;;:         `;;;;;;;;;+                                      
                               +;;;;;;;;;           ';;;;;;;;;`                                     
                               ;;;;;;;;;+           ';;;;;;;;;:                                     
                              ';;;;;;;;;;           ,;;;;;;;;;+                                     
                              ':;;;;;;;'             +;;;;;;;;;                                     
                             .;:;;;;;;;'             +;;;;;;;;;:                                    
                             +;;;;;;;;;`             .;;;;;;;;;+                                    
                            `;;;;;;;;;+               ;:;;;;;;;;`                                   
                            ;;;;;;;;;;.               +;;;;;;;::.                                   
                            ';;;;;;;;'`               :;;;;;;;;:+                                   
                           :;;;;;;;;:'                ';;;;;;;;;'                                   
                           ';;;;;;;;'`                +#;;;;;;;;;`                                  
                          `;;;;;;;;;+                 '';;;;;;;;;+                                  
                          +;;;;;;;;;.                '::;;;;;;;;;+                                  
                          ;;;;;;;;;+                 #:'';;;;;;;;;`                                 
                         .#;;;;;;;;'                `;:+;;;;;;;;;;;                                 
                         ':'';;;;;;                 '::.,;;;;;;;;;+                                 
                        +::::+';;;+                 ':'  +:;;;;;;;;`                                
                       `;;;::::;#+:                `;:+  +;;;;;;;:;;      '#+,                      
                       +#::::::::;'`               +:;,  `;;;;:;;'#';;;;;::;:'`                     
                      ';:''::::::::#`              +:'    ';:;;+'::;;:;::::::''                     
                      #+::;+':::::::'.            .:;+    '''+;::;:;:::;:::;':'                     
                    `';+';;:;'';:::::':    '      +::.     +:::::::::::::;#;:#                      
                    :+;#'.''##;#;:;;:::'+  #     `+;'      ;:;::::::::;'+;:'+                       
                   '#;+". ` `+:;+:;::;::+'#+     +:;#     ';:::;:+#+';:::+.                        
                   ';#''      ,+::+#';::;+'#+    ';::      #:;;'+';'''++:`                          
                                '':::;'''#+     ,:;;`      #';:;;:+                                 
                                 `:'++;;':       :++       .;;:;;#,                                 
                                       `                    '':``                                   


*/
