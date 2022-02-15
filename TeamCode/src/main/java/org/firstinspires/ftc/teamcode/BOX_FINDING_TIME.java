package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.Locale;

@Autonomous
public class BOX_FINDING_TIME extends LinearOpMode {
    protected DcMotor leftFront, leftRear, rightFront, rightRear;
    BNO055IMU imu;

    private ColorSensor sensorB;

    private static final int
            HUE = 0,
            SATURATION = 1,
            VALUE = 2;


    Orientation angles;
    Acceleration gravity, acceleration;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new NaiveAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Set up our telemetry dashboard
        //composeTelemetry();
        float[] BackHSV;

        waitForStart();

        //imu.startAccelerationIntegration(new Position(), new Velocity(), 50);

        Orientation current, previous;
        double next;
        current = imu.getAngularOrientation();

        do {
            mecanum(0, 0, -.5);
            previous = current;
            current = imu.getAngularOrientation();
            //telemetry.update();
            next = current.firstAngle + current.firstAngle - previous.firstAngle;
        }
        while (next < 166 && previous.firstAngle >= 0 && opModeIsActive());

        fullStop();

       sleep(1000);

       do{
           mecanum(Math.PI/2, .25, 0);
           BackHSV = readColorSensor(sensorB);
       }
       while (opModeIsActive() && BackHSV[HUE] < 15 || BackHSV[HUE] > 25);

       fullStop();

       sleep(1000);

        do {
            mecanum(0, 0, -.5);
            previous = current;
            current = imu.getAngularOrientation();
            //telemetry.update();
            next = current.firstAngle + current.firstAngle - previous.firstAngle;
        }
        while (next < 166 && previous.firstAngle >= 0 && opModeIsActive());

        fullStop();

        sleep(1000);

       do{
           mecanum(Math.PI,.25,0);
           BackHSV = readColorSensor(sensorB);
       }
       while(opModeIsActive() && BackHSV[HUE] > 15 && BackHSV[HUE] < 25);

       fullStop();

       sleep(500);

        do {
            mecanum(0, 0, -.25);
            previous = current;
            current = imu.getAngularOrientation();
            //telemetry.update();
            next = current.firstAngle + current.firstAngle - previous.firstAngle;
        }
        while (next < 166 && previous.firstAngle >= 0 && opModeIsActive());

        fullStop();

        sleep(1000);

       Orientation actual, desired = imu.getAngularOrientation();
       double correction = 0;

        do {
            actual = imu.getAngularOrientation();
            if(actual.firstAngle > desired.firstAngle){
                correction = .01;
            }
            else if (actual.firstAngle < desired.firstAngle){
                correction = -.01;
            }
            else{
                correction= 0;
            }
            mecanum(-4.27, .6, correction);
            BackHSV = readColorSensor(sensorB);
        }

        while (opModeIsActive() && BackHSV[HUE] < 220 || BackHSV[HUE] > 225);

        fullStop();
        sleep(500);
/*
        do {
            mecanum(0, 0, -.75);
            previous = current;
            current = imu.getAngularOrientation();
            //telemetry.update();
            next = current.firstAngle + current.firstAngle - previous.firstAngle;
        }
        while (next <= 180 && previous.firstAngle >= 0 && opModeIsActive());
*/
      /*  fullStop();

        mecanum(0, .25, 0);

        sleep(1000);

        fullStop();
        
       */

    }

    private void fullStop() {
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
    }

    public void initialize() {
        leftFront = hardwareMap.get(DcMotor.class, "left front");
        leftRear = hardwareMap.get(DcMotor.class, "left rear");
        rightFront = hardwareMap.get(DcMotor.class, "right front");
        rightRear = hardwareMap.get(DcMotor.class, "right rear");

        sensorB = hardwareMap.get(ColorSensor.class, "sensor");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    protected void mecanum(double heading, double speed, double rotation) {
        heading = heading - Math.PI / 4;
        leftFront.setPower(Math.sin(heading) * speed - rotation);
        leftRear.setPower(Math.cos(heading) * speed - rotation);
        rightFront.setPower(Math.cos(heading) * speed + rotation);
        rightRear.setPower(Math.sin(heading) * speed + rotation);
    }

    void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() {
            @Override
            public void run() {
                // Acquiring the angles is relatively expensive; we don't want
                // to do that in each of the three items that need that info, as that's
                // three times the necessary expense.
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
            }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override
                    public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override
                    public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override
                    public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override
                    public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override
                    public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override
                    public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override
                    public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel * gravity.xAccel
                                        + gravity.yAccel * gravity.yAccel
                                        + gravity.zAccel * gravity.zAccel));
                    }
                });
        telemetry.addLine()
                .addData("position", new Func<String>() {
                    @Override
                    public String value() {
                        Position pos = imu.getPosition();
                        return pos.x + ", " + pos.y + ", " + pos.z;
                    }
                });
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    private float[] readColorSensor(ColorSensor sensor){
        float[] hsv = new float[3];

        Color.RGBToHSV(sensor.red(), sensor.green(), sensor.blue(), hsv);

        return hsv;
    }
}
