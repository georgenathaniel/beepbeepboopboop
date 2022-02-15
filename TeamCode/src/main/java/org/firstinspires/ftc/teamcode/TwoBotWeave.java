package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Config
public class TwoBotWeave extends MecanumDrive {
    public static  double RECEIVING_POSITION = .785;
    public static  double CARRY_POSITION = .880;
    private Servo servo;
    private double position;

    @Override
    public void init() {
        super.init();
       servo = hardwareMap.get(Servo.class, "Servo");
       position = 0.5;
    }

    @Override
    public void loop() {
        super.loop();
        servo.setPosition(position);
        if (gamepad1.dpad_left) {
            position -= 0.0005;
        }
        if (gamepad1.dpad_right) {
            position += 0.0005;
        }
        if (gamepad1.a) {
            position = RECEIVING_POSITION;
        }
        if (gamepad1.b) {
            position = CARRY_POSITION;
        }

        telemetry.addData("Servo pos", servo.getPosition());

    }

}
