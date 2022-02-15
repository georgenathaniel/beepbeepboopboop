package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class DriveBase extends OpMode {
    protected DcMotor leftFront, leftRear, rightFront, rightRear;

    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class,  "left front");
        leftRear = hardwareMap.get(DcMotor.class,  "left rear");
        rightFront = hardwareMap.get(DcMotor.class, "right front");
        rightRear = hardwareMap.get(DcMotor.class, "right rear");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
