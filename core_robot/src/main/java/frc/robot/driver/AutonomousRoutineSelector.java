package frc.robot.driver;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IControlTask;
import frc.lib.driver.TrajectoryManager;
import frc.lib.mechanisms.LoggingManager;
import frc.lib.robotprovider.Alliance;
import frc.lib.robotprovider.IDriverStation;
import frc.lib.robotprovider.ILogger;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.*;
import frc.robot.LoggingKey;
import frc.robot.driver.SmartDashboardSelectionManager.AutoRoutine;
import frc.robot.driver.SmartDashboardSelectionManager.StartPosition;
import frc.robot.driver.controltasks.*;

@Singleton
public class AutonomousRoutineSelector
{
    private final ILogger logger;

    private final TrajectoryManager trajectoryManager;
    private final SmartDashboardSelectionManager selectionManager;
    private final IDriverStation driverStation;
    private final AutonLocManager locManager;

    /**
     * Initializes a new AutonomousRoutineSelector
     */
    @Inject
    public AutonomousRoutineSelector(
        LoggingManager logger,
        TrajectoryManager trajectoryManager,
        SmartDashboardSelectionManager selectionManager,
        IRobotProvider provider)
    {
        this.logger = logger;
        this.trajectoryManager = trajectoryManager;
        this.selectionManager = selectionManager;

        this.driverStation = provider.getDriverStation();

        this.locManager = new AutonLocManager(provider);
    }

    /**
     * Check what routine we want to use and return it
     * 
     * @param mode that is starting
     * @return autonomous routine to execute during autonomous mode
     */
    public IControlTask selectRoutine(RobotMode mode)
    {
        String driverStationMessage = this.driverStation.getGameSpecificMessage();
        this.logger.logString(LoggingKey.AutonomousDSMessage, driverStationMessage);
        if (mode == RobotMode.Test)
        {
            // currently not supported...
            return new WaitTask(0.0);
        }

        if (mode == RobotMode.Autonomous)
        {
            this.locManager.updateAlliance();
            StartPosition startPosition = this.selectionManager.getSelectedStartPosition();
            AutoRoutine routine = this.selectionManager.getSelectedAutoRoutine();

            boolean isRed = this.locManager.getIsRed();
            Alliance alliance = isRed ? Alliance.Red : Alliance.Blue;

            this.logger.logString(LoggingKey.AutonomousSelection, startPosition.toString() + "." + routine.toString());

            return GetFillerRoutine();
        }

        return GetFillerRoutine();
    }

    /**
     * Gets an autonomous routine that does nothing
     */
    private static IControlTask GetFillerRoutine()
    {
        return new WaitTask(0.0);
    }
}

/*
 * @formatter:off




































































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
           _             _      __        __    _     _       
__      _| |__  _   _  (_)___  \ \      / /_ _| | __| | ___  
\ \ /\ / / '_ \| | | | | / __|  \ \ /\ / / _` | |/ _` |/ _ \ 
 \ V  V /| | | | |_| | | \__ \   \ V  V / (_| | | (_| | (_) |
 _\_/\_/ |_| |_|\__, | |_|___/    \_/\_/ \__,_|_|\__,_|\___/ 
| |__   ___ _ __|___/                                        
| '_ \ / _ \ '__/ _ \                                        
| | | |  __/ | |  __/                                        
|_| |_|\___|_|  \___|     

 * @formatter:on
*/