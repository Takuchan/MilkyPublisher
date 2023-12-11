package com.takuchan.milkypublisher.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions;
import com.takuchan.milkypublisher.R;

public class PreferenceUtils {
    private static final int POSE_DETECTOR_PERFORMANCE_MODE_FAST = 1;

    static void saveString(Context context, @StringRes int prefKeyId, @Nullable String value){
        context.getSharedPreferences("MLKitPoseDetection", Context.MODE_PRIVATE)
                .edit()
                .putString(context.getString(prefKeyId), value)
                .apply();
    }

    public static PoseDetectorOptionsBase getPoseDetectorOptionsForLivePreview(Context context){
        int performanceMode = getModeTypePreferenceValue(
                context,
                R.string.pref_key_live_preview_pose_detection_performance_mode,
                POSE_DETECTOR_PERFORMANCE_MODE_FAST);
        boolean preferGPU = preferGPUForPoseDetection(context);
        if(performanceMode == POSE_DETECTOR_PERFORMANCE_MODE_FAST){
            PoseDetectorOptions.Builder builder =
                    new PoseDetectorOptions.Builder()
                    .setDetectorMode(PoseDetectorOptions.STREAM_MODE);
            if(preferGPU){
                builder.setPreferredHardwareConfigs(PoseDetectorOptions.CPU_GPU);
            }
            return builder.build();
        }else{
            AccuratePoseDetectorOptions.Builder builder =
                    new AccuratePoseDetectorOptions.Builder()
                    .setDetectorMode(PoseDetectorOptions.STREAM_MODE);
        }

    }
    public static boolean preferGPUForPoseDetection(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String prefKey = context.getString(R.string.pref_key_pose_detector_prefer_gpu);
        return sharedPreferences.getBoolean(prefKey, true);
    }
    private static int getModeTypePreferenceValue(
            Context context, @StringRes int prefKeyResId, int defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String prefKey = context.getString(prefKeyResId);
        return Integer.parseInt(sharedPreferences.getString(prefKey, String.valueOf(defaultValue)));
    }
}
