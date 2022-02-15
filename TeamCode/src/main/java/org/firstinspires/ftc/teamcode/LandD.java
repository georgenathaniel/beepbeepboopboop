package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
class Robotcode extends TankDrive {
    private Servo servo;
    private double position;

    @Override
    public void init() {
        super.init();
        servo = hardwareMap.get(Servo.class, "Servo");

    }

    public void loop() {
        super.loop();
        servo.setPosition(position);
        if (gamepad1.dpad_left) {
            position -= 0.0005;
        }
        if (gamepad1.dpad_right) {
            position += 0.0005;
        }
    }
}
