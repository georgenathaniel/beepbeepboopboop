//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.qualcomm.hardware.bosch.BNO055IMU.AccelerationIntegrator;
import com.qualcomm.hardware.bosch.BNO055IMU.Parameters;
import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.NavUtil;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class NaiveAccelerationIntegrator implements AccelerationIntegrator {
    Parameters parameters = null;
    Position position = new Position();
    Velocity velocity = new Velocity();
    Acceleration acceleration = null;

    public Position getPosition() {
        return this.position;
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    public Acceleration getAcceleration() {
        return this.acceleration;
    }

    public NaiveAccelerationIntegrator() {
    }

    public void initialize(@NonNull Parameters parameters, @Nullable Position initialPosition, @Nullable Velocity initialVelocity) {
        this.parameters = parameters;
        this.position = initialPosition != null ? initialPosition : this.position;
        this.velocity = initialVelocity != null ? initialVelocity : this.velocity;
        this.acceleration = null;
    }

    public void update(Acceleration linearAcceleration) {
        if (linearAcceleration.acquisitionTime != 0L) {
            if (this.acceleration != null) {
                Acceleration accelPrev = this.acceleration;
                Velocity velocityPrev = this.velocity;
                this.acceleration = linearAcceleration;
                if (accelPrev.acquisitionTime != 0L) {
                    Velocity deltaVelocity = NavUtil.meanIntegrate(this.acceleration, accelPrev);
                    this.velocity = NavUtil.plus(this.velocity, deltaVelocity);
                }

                if (velocityPrev.acquisitionTime != 0L) {
                    Position deltaPosition = NavUtil.meanIntegrate(this.velocity, velocityPrev);
                    this.position = NavUtil.plus(this.position, deltaPosition);
                }

                if (this.parameters != null && this.parameters.loggingEnabled) {
                    RobotLog.vv(this.parameters.loggingTag, "dt=%.3fs accel=%s vel=%s pos=%s", new Object[]{(double)(this.acceleration.acquisitionTime - accelPrev.acquisitionTime) * 1.0E-9D, this.acceleration, this.velocity, this.position});
                }
            } else {
                this.acceleration = linearAcceleration;
            }
        }

    }
}
