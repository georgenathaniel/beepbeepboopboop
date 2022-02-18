package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class RobottimeFAST extends TankDrive {
   // private Servo servo;
   // private double position;
    private double extendpower;
    private double updownpower;


    @Override
    public void init() {
        super.init();
       // servo = hardwareMap.get(Servo.class, "Servo");
        //position = 0.5;
        extendpower = 0;
        updownpower = 0;
    }

    public void loop() {
        super.loop();

        extend.setPower(extendpower);
        if (gamepad1.dpad_right) {
            extendpower = 0.25;
        }
        if (gamepad1.dpad_left) {
            extendpower = -0.25;
        }
        else {
            extendpower = 0;
        }

        updown.setPower(updownpower);
        if (gamepad1.dpad_up) {
            updownpower = 0.5;
        }
        if (gamepad1.dpad_down) {
            updownpower = -0.5;
        }
        else {
            updownpower = 0;
        }
       /* servo.setPosition(position);
        if (gamepad1.a) {
            position -= 0.0005;
        }
        if (gamepad1.b) {
            position += 0.0005;
        }*/

    }
}

