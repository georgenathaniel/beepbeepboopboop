package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class RobottimeFAST extends TankDrive {

    @Override
    public void init() {
        super.init();

    }

    public void loop() {
        super.loop();
        double extendpower = gamepad1.left_stick_y;
        double updownpower = gamepad1.right_stick_y;

        extend.setPower(extendpower);
        updown.setPower(extendpower);
    }
}

