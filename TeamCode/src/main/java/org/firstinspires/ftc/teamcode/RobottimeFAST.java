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
        double leftPower = gamepad1.left_stick_y;
        double rightPower = gamepad1.right_stick_y;

        extend.setPower(leftPower);
        updown.setPower(leftPower);
    }
}

