package frc.robot.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.PIDConstant;

public class MotorDashboardUpdater {

    private final String positionKey;
    private final String velocityKey;
    private final String invertedKey;
    private final String pGainKey;
    private final String iGainKey;
    private final String dGainKey;
    private final String iZoneKey;
    private final String feedForwardKey;
    private final String minOutputKey;
    private final String maxOutputKey;

    private final RelativeEncoder encoder;
    private final SparkMaxPIDController controller;

    private double pGainCurrent;
    private double iGainCurrent;
    private double dGainCurrent;
    private double iZoneCurrent;
    private double feedForwardCurrent;
    private double minOutputCurrent;
    private double maxOutputCurrent;

    public MotorDashboardUpdater(String name, CANSparkMax motor, PIDConstant pidValues) {

        positionKey = name + " Position";
        velocityKey = name + " Rotation";
        invertedKey = name + " Inverted?";
        pGainKey = name + " P Gain";
        iGainKey = name + " I Gain";
        dGainKey = name + " D Gain";
        iZoneKey = name + " I Zone";
        feedForwardKey = name + " Feed Forward";
        minOutputKey = name + " Min Output";
        maxOutputKey = name + " Max Output";

        encoder = motor.getEncoder();

        if (pidValues != null) {
            pGainCurrent = pidValues.getP();
            iGainCurrent = pidValues.getI();
            dGainCurrent = pidValues.getD();
            iZoneCurrent = pidValues.getIZone();
            feedForwardCurrent = pidValues.getFeedForward();
            minOutputCurrent = pidValues.getMinOutput();
            maxOutputCurrent = pidValues.getMaxOutput();
            controller = motor.getPIDController();
        } else {
            controller = null;
        }
    }
    
    public void updateDashboard() {

        SmartDashboard.putNumber(positionKey, encoder.getPosition());
        SmartDashboard.putNumber(velocityKey, encoder.getVelocity());
        SmartDashboard.putBoolean(invertedKey, encoder.getInverted());

        if (controller != null) {

            double pGainNew = SmartDashboard.getNumber(pGainKey, pGainCurrent);
            if (pGainNew != pGainCurrent) {
                controller.setP(pGainNew);
            }

            double iGainNew = SmartDashboard.getNumber(iGainKey, iGainCurrent);
            if (iGainNew != iGainCurrent) {
                controller.setI(iGainNew);
            }

            double dGainNew = SmartDashboard.getNumber(dGainKey, dGainCurrent);
            if (dGainNew != dGainCurrent) {
                controller.setD(dGainNew);
            }

            double iZoneNew = SmartDashboard.getNumber(iZoneKey, iZoneCurrent);
            if (iZoneNew != iZoneCurrent) {
                controller.setIZone(iZoneNew);
            }

            double feedForwardNew = SmartDashboard.getNumber(feedForwardKey, feedForwardCurrent);
            if (feedForwardNew != feedForwardCurrent) {
                controller.setFF(feedForwardNew);
            }
            
            double minOutputNew = SmartDashboard.getNumber(minOutputKey, minOutputCurrent);
            double maxOutputNew = SmartDashboard.getNumber(maxOutputKey, maxOutputCurrent);
            if (minOutputNew != minOutputCurrent || maxOutputNew != maxOutputCurrent) {
                controller.setOutputRange(minOutputNew, maxOutputNew);
            }
        }
    }
}
