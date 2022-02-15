package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class LineFollower extends MecanumDrive {
    private static final int
        HUE = 0,
        SATURATION = 1,
        VALUE = 2;

    private int left;
    private int right;

    private ColorSensor sensorL, sensorR;

    private boolean readyup;
    private boolean readydown;
    private Telemetry dashboard;

    private double turn = .3;

    public LineFollower() {
    }

    @Override
    public void init() {
        super.init();
        sensorL = hardwareMap.get(ColorSensor.class, "sensorL");
        sensorR = hardwareMap.get(ColorSensor.class, "sensorR");
        readyup = true;
        readydown = true;
        left = 0;
        right = 0;
        dashboard = FtcDashboard.getInstance().getTelemetry();
    }

    @Override
    public void loop() {
        float[] RightHSV = readColorSensor(sensorR),
                LeftHSV = readColorSensor(sensorL);

        telemetry.addData("sensorR", humanReadable(RightHSV));
        telemetry.addData("SensorR is blue", RightHSV[HUE] > 180 && RightHSV[HUE] < 210);
        telemetry.addData("sensorL", humanReadable(LeftHSV));
        telemetry.addData("SensorL is blue", LeftHSV[HUE] > 180 && LeftHSV[HUE] < 210);
        telemetry.addData("Turn value is:", turn);


        dashboard.addData("left hue", LeftHSV[HUE]);
        dashboard.addData("right hue", RightHSV[HUE]);
        dashboard.update();


        if (gamepad1.dpad_up) {
            if(readyup){
                turn +=.1;
                readyup = false;
            }
        }
        else{
            readyup = true;
        }

        if(gamepad1.dpad_down){
            if(readydown){
                turn -=.1;
                readydown = false;
            }
        }
        else{
            readydown = true;
        }

        if (gamepad1.x) {
            if (LeftHSV[HUE] > 180 && LeftHSV[HUE] < 210) {
                left = 20;
            }
            if (RightHSV[HUE] > 180 && RightHSV[HUE] < 210) {
                right = 20;
            }
            if(left > 0 && right == 0){
                mecanum(0, 0, -turn);
                left --;
            }
            else if(right > 0 && left == 0){
                mecanum(0, 0, turn);
                right --;
            }
            else if(right > 0 && left > 0){
                if(right > left){
                    left = 0;
                }
                else {
                    right = 0;
                }
            }
            else{
                mecanum(-Math.PI / 2, 0.25, 0);
            }
        } else {
            super.loop();
        }
    }

    private String humanReadable(float[] hsv) {
        return "[H:" + hsv[HUE] + ", S:" + hsv[SATURATION] + ", V:" + hsv[VALUE] + "]";
        // [H:173.45, S:0.734, V:0.912]
    }

    private float[] readColorSensor(ColorSensor sensor){
        float[] hsv = new float[3];

        Color.RGBToHSV(sensor.red(), sensor.green(), sensor.blue(), hsv);

        return hsv;
    }
}
