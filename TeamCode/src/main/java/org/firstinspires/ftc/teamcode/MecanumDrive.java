package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp
public class MecanumDrive extends DriveBase {

    @Override
    public void loop() {
        double heading = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);

        double speed = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);

        double rotation = gamepad1.right_stick_x;

        mecanum(heading, speed, rotation);
    }

    protected void mecanum(double heading, double speed, double rotation) {
        heading = heading - Math.PI / 4;
        leftFront.setPower(Math.sin(heading) * speed - rotation);
        leftRear.setPower(Math.cos(heading) * speed - rotation);
        rightFront.setPower(Math.cos(heading) * speed + rotation);
        rightRear.setPower(Math.sin(heading) * speed + rotation);
    }
}
