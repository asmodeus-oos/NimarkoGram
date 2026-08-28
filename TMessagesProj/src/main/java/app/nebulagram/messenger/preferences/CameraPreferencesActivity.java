/*
 * This file is part of NebulaGram for Android.
 * Licensed under GNU GPL v2 or later. See LICENSE.
 * Copyright Ettacent, 2026.
 */

package app.nebulagram.messenger.preferences;

import org.telegram.messenger.BuildVars;
import static org.telegram.messenger.LocaleController.getString;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;

import org.telegram.messenger.R;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;

import java.util.ArrayList;
import java.util.stream.Collectors;

import app.nebulagram.messenger.camera.CameraTypeSelector;
import app.nebulagram.messenger.camera.CameraXUtils;
import app.nebulagram.messenger.NebulaConfig;
import app.nebulagram.messenger.preferences.helpers.PopupHelper;
import app.nebulagram.messenger.preferences.helpers.SettingsHelper;
import app.nebulagram.messenger.utils.ResourcesUtils;

public class CameraPreferencesActivity extends NebulaUniversalPreferencesActivity {

    private final int cameraTypeSelectorRow = 2;

    private final int disableAttachCameraRow = 1;

    private final int cameraUseDualCameraRow = 3;
    private final int rearCamRow = 4;
    private final int startFromUltraWideRow = 5;
    private final int cameraStabilisationRow = 6;
    private final int cameraXQualityRow = 7;
    private final int cameraXFpsRangeRow = 8;

    private final int cameraControlButtonsRow = 10;

    private final int cameraImprovementsRow = 11;
    private final int opticalStabilizationRow = 12;
    private final int continuousFocusRow = 13;
    private final int noiseReductionRow = 14;
    private final int faceDetectionRow = 15;
    private final int useHighRangeRow = 16;

    private final int roundVideoSizeRow = 17;
    private final int roundVideoBitrateRow = 18;

    private boolean cameraImprovementsExpanded = false;

    @Override
    protected CharSequence getTitle() {
        return getString(R.string.CP_Category_Camera);
    }

    @Override
    public View createView(Context context) {
        setMD3(true);
        return super.createView(context);
    }

    @Override
    public void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        final boolean cameraX = CameraXUtils.isCurrentCameraCameraX();
        final boolean camera2 = app.nebulagram.messenger.NebulaConfig.cameraType == NebulaConfig.CAMERA_2;
        final boolean advanced = cameraX || camera2;

        if (CameraXUtils.isCameraXSupported()) {
            items.add(UItem.asHeader(getString(R.string.CP_CameraType)));
            items.add(SettingsHelper.asCustomWithBackground(cameraTypeSelectorRow, new CameraTypeSelector(getContext()) {
                @Override
                protected void onSelectedCamera(int cameraSelected) {
                    super.onSelectedCamera(cameraSelected);
                    NebulaConfig.setCameraType(cameraSelected);
                    listView.adapter.update(true);
                }
            }));
            items.add(UItem.asShadow(getCameraAdvise()));
        }

        items.add(UItem.asHeader(getString(R.string.CP_Category_Camera)));
        items.add(SettingsHelper.asSwitchCG(cameraControlButtonsRow, getString(R.string.CP_CenterCameraControlButtons), getString(R.string.CP_CenterCameraControlButtons_Desc))
                .setChecked(app.nebulagram.messenger.NebulaConfig.centerCameraControlButtons)
        );
        if (BuildVars.DEBUG_VERSION) {
            items.add(SettingsHelper.asSwitchCG(disableAttachCameraRow, getString(R.string.CP_DisableCam), getString(R.string.CP_DisableCam_Desc))
                    .setChecked(app.nebulagram.messenger.NebulaConfig.disableAttachCamera)
            );
        }
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(getString(R.string.CP_Header_Videomessages)));
        items.add(UItem.asButton(rearCamRow, getString(R.string.NM_CAM_RoundCamera), getRoundCameraText()));
        if (advanced) {
            items.add(SettingsHelper.asSwitchCG(cameraUseDualCameraRow, getString(R.string.CP_CameraDualCamera), getString(R.string.CP_CameraDualCamera_Desc))
                    .setChecked(app.nebulagram.messenger.NebulaConfig.useDualCamera)
            );
        }
        if (cameraX) {
            items.add(SettingsHelper.asSwitchCG(startFromUltraWideRow, getString(R.string.CP_CameraUW), getString(R.string.CP_CameraUW_Desc))
                    .setChecked(app.nebulagram.messenger.NebulaConfig.startFromUltraWideCam)
            );
        }
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader(getString(R.string.NM_CAM_VideoQuality)));
        items.add(UItem.asButton(roundVideoSizeRow, getString(R.string.NM_CAM_RoundVideoSize), getRoundVideoSizeText()));
        items.add(UItem.asButton(roundVideoBitrateRow, getString(R.string.NM_CAM_RoundVideoBitrate), getRoundVideoBitrateText()));
        if (advanced) {
            items.add(UItem.asButton(cameraXQualityRow, getString(R.string.CP_CameraQuality),
                    getCameraQualityText(app.nebulagram.messenger.NebulaConfig.cameraResolution)));
            items.add(UItem.asButton(cameraXFpsRangeRow, getString(R.string.NM_CAM_FpsRange), getCameraXFpsRange()));
            items.add(SettingsHelper.asSwitchCG(cameraStabilisationRow, getString(R.string.CP_CameraStabilisation))
                    .setChecked(app.nebulagram.messenger.NebulaConfig.cameraStabilisation)
            );
        }

        if (advanced) {
            items.add(UItem.asShadowCollapseButton(cameraImprovementsRow, getString(R.string.NM_CAM_Improvements) + "  ")
                    .setCollapsed(!cameraImprovementsExpanded));
            if (cameraImprovementsExpanded) {
                items.add(SettingsHelper.asSwitchCG(opticalStabilizationRow, getString(R.string.NM_CAM_OpticalStabilization), getString(R.string.NM_CAM_OpticalStabilization_Desc))
                        .setChecked(app.nebulagram.messenger.NebulaConfig.cameraOpticalStabilization)
                );
                items.add(SettingsHelper.asSwitchCG(continuousFocusRow, getString(R.string.NM_CAM_ContinuousFocus), getString(R.string.NM_CAM_ContinuousFocus_Desc))
                        .setChecked(app.nebulagram.messenger.NebulaConfig.cameraContinuousFocus)
                );
                items.add(SettingsHelper.asSwitchCG(noiseReductionRow, getString(R.string.NM_Camera_NoiseReduction), getString(R.string.NM_Camera_NoiseReduction_Desc))
                        .setChecked(app.nebulagram.messenger.NebulaConfig.cameraNoiseReduction)
                );
                items.add(SettingsHelper.asSwitchCG(faceDetectionRow, getString(R.string.NM_Camera_FaceDetection), getString(R.string.NM_Camera_FaceDetection_Desc))
                        .setChecked(app.nebulagram.messenger.NebulaConfig.cameraFaceDetection)
                );
                items.add(SettingsHelper.asSwitchCG(useHighRangeRow, getString(R.string.NM_Camera_UseHighRange), getString(R.string.NM_Camera_UseHighRange_Desc))
                        .setChecked(app.nebulagram.messenger.NebulaConfig.cameraXUseHighRange)
                );
            }
        }
        items.add(UItem.asShadow(null));
    }

    @Override
    public CameraPreferencesActivity openAtSetting(int itemId) {
        if (itemId >= opticalStabilizationRow && itemId <= useHighRangeRow) {
            cameraImprovementsExpanded = true;
        }
        super.openAtSetting(itemId);
        return this;
    }

    @Override
    public void onClick(UItem item, View view, int position, float x, float y) {
        if (item.id == disableAttachCameraRow) {
            NebulaConfig.toggleDisableAttachCamera();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.disableAttachCamera);

            showRestartBulletin();
        } else if (item.id == cameraUseDualCameraRow) {
            NebulaConfig.toggleUseDualCamera();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.useDualCamera);

            if (CameraXUtils.isCurrentCameraNotCameraX()) listView.adapter.update(true);
        } else if (item.id == rearCamRow) {
            ArrayList<CharSequence> opts = new ArrayList<>();
            opts.add(getString(R.string.NM_CAM_FrontCamera));
            opts.add(getString(R.string.NM_CAM_RearCamera));
            opts.add(getString(R.string.NM_CAM_AskCamera));
            PopupHelper.showLegacy(opts, getString(R.string.NM_CAM_RoundCamera), NebulaConfig.videoMessagesCamera, getContext(), i -> {
                NebulaConfig.setVideoMessagesCamera(i);
                SettingsHelper.updateButtonValue(view, getRoundCameraText());
            });
        } else if (item.id == startFromUltraWideRow) {
            NebulaConfig.toggleStartFromUltraWideCam();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.startFromUltraWideCam);
        } else if (item.id == cameraStabilisationRow) {
            NebulaConfig.toggleCameraStabilisation();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.cameraStabilisation);
        } else if (item.id == cameraXFpsRangeRow) {
            ArrayList<String> configStringKeys = new ArrayList<>();
            ArrayList<Integer> configValues = new ArrayList<>();

            configStringKeys.add("25-30");
            configValues.add(NebulaConfig.CameraXFpsRange25to30);

            configStringKeys.add("30-30");
            configValues.add(NebulaConfig.CameraXFpsRange30to30);

            if (isExtendedFpsAvailable()) {
                configStringKeys.add("30-60");
                configValues.add(NebulaConfig.CameraXFpsRange30to60);
            }

            configStringKeys.add(getString(R.string.Default));
            configValues.add(NebulaConfig.CameraXFpsRangeDefault);

            PopupHelper.showLegacy(configStringKeys, "FPS", configValues.indexOf(app.nebulagram.messenger.NebulaConfig.cameraXFpsRange), getContext(), i -> {
                NebulaConfig.setCameraXFpsRange(configValues.get(i));
                SettingsHelper.updateButtonValue(view, getCameraXFpsRange());
            });
        } else if (item.id == cameraXQualityRow) {
            ArrayList<Integer> types = getAvailableCameraQualityHeights();
            ArrayList<Integer> finalTypes = types;
            ArrayList<String> labels = finalTypes.stream().map(this::getCameraQualityText)
                    .collect(Collectors.toCollection(ArrayList::new));
            PopupHelper.showLegacy(labels, getString(R.string.CP_CameraQuality),
                    finalTypes.indexOf(app.nebulagram.messenger.NebulaConfig.cameraResolution), getContext(), i -> {
                NebulaConfig.setCameraResolution(finalTypes.get(i));
                SettingsHelper.updateButtonValue(view,
                        getCameraQualityText(app.nebulagram.messenger.NebulaConfig.cameraResolution));
            });
        } else if (item.id == roundVideoSizeRow) {
            ArrayList<CharSequence> labels = new ArrayList<>();
            ArrayList<Integer> values = new ArrayList<>();
            labels.add(getString(R.string.NM_CAM_RoundVideoSize_Auto)); values.add(NebulaConfig.ROUND_AUTO);
            labels.add(getString(R.string.NM_CAM_RoundVideoSize_SD));   values.add(NebulaConfig.ROUND_SD);
            labels.add(getString(R.string.NM_CAM_RoundVideoSize_STD));  values.add(NebulaConfig.ROUND_STD);
            labels.add(getString(R.string.NM_CAM_RoundVideoSize_HD));   values.add(NebulaConfig.ROUND_HD);
            int cur = values.indexOf(NebulaConfig.videoMessagesResolution);
            if (cur < 0) cur = values.indexOf(NebulaConfig.ROUND_STD);
            PopupHelper.showLegacy(labels, getString(R.string.NM_CAM_RoundVideoSize), cur, getContext(), i -> {
                NebulaConfig.setVideoMessagesResolution(values.get(i));
                SettingsHelper.updateButtonValue(view, getRoundVideoSizeText());
            });
        } else if (item.id == roundVideoBitrateRow) {
            ArrayList<CharSequence> labels = new ArrayList<>();
            ArrayList<Integer> values = new ArrayList<>();
            labels.add("1000 kbps");  values.add(1000);
            labels.add("1500 kbps");  values.add(1500);
            labels.add("2200 kbps");  values.add(2200);
            labels.add("3000 kbps");  values.add(3000);
            labels.add("4000 kbps");  values.add(4000);
            int cur = values.indexOf(NebulaConfig.videoMessagesBitrateKbps);
            if (cur < 0) cur = values.indexOf(2200);
            PopupHelper.showLegacy(labels, getString(R.string.NM_CAM_RoundVideoBitrate), cur, getContext(), i -> {
                NebulaConfig.setVideoMessagesBitrateKbps(values.get(i));
                SettingsHelper.updateButtonValue(view, getRoundVideoBitrateText());
            });
        } else if (item.id == cameraControlButtonsRow) {
            NebulaConfig.toggleCenterCameraControlButtons();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.centerCameraControlButtons);
        } else if (item.id == cameraImprovementsRow) {
            cameraImprovementsExpanded = !cameraImprovementsExpanded;
            listView.adapter.update(true);
        } else if (item.id == opticalStabilizationRow) {
            NebulaConfig.toggleCameraOpticalStabilization();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.cameraOpticalStabilization);
        } else if (item.id == continuousFocusRow) {
            NebulaConfig.toggleCameraContinuousFocus();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.cameraContinuousFocus);
        } else if (item.id == noiseReductionRow) {
            NebulaConfig.toggleCameraNoiseReduction();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.cameraNoiseReduction);
        } else if (item.id == faceDetectionRow) {
            NebulaConfig.toggleCameraFaceDetection();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.cameraFaceDetection);
        } else if (item.id == useHighRangeRow) {
            NebulaConfig.toggleCameraXUseHighRange();
            updateCheckState(view, app.nebulagram.messenger.NebulaConfig.cameraXUseHighRange);
            if (!app.nebulagram.messenger.NebulaConfig.cameraXUseHighRange
                    && app.nebulagram.messenger.NebulaConfig.cameraXFpsRange
                    == NebulaConfig.CameraXFpsRange30to60) {
                NebulaConfig.setCameraXFpsRange(NebulaConfig.CameraXFpsRange30to30);
                listView.adapter.update(true);
            }
        }
    }

    @Override
    public boolean onLongClick(UItem item, View view, int position, float x, float y) {
        return false;
    }

    private boolean isExtendedFpsAvailable() {
        return app.nebulagram.messenger.NebulaConfig.cameraXUseHighRange;
    }

    private ArrayList<Integer> getAvailableCameraQualityHeights() {
        ArrayList<Integer> result = new ArrayList<>(3);
        result.add(NebulaConfig.CAMERA_RESOLUTION_2K);
        result.add(NebulaConfig.CAMERA_RESOLUTION_1080P);
        result.add(NebulaConfig.CAMERA_RESOLUTION_720P);
        return result;
    }

    private String getCameraQualityText(int height) {
        if (height == NebulaConfig.CAMERA_RESOLUTION_2K) {
            return getString(R.string.Quality1440Short);
        }
        return height + "p";
    }

    public static String getCameraName() {
        return switch (app.nebulagram.messenger.NebulaConfig.cameraType) {
            case NebulaConfig.TELEGRAM_CAMERA -> "Telegram";
            case NebulaConfig.CAMERA_X -> "CameraX";
            case NebulaConfig.CAMERA_2 -> "Camera 2 (Telegram)";
            default -> getString(R.string.CP_CameraTypeSystem);
        };
    }

    private CharSequence getCameraAdvise() {
        String advise = switch (app.nebulagram.messenger.NebulaConfig.cameraType) {
            case NebulaConfig.TELEGRAM_CAMERA -> getString(R.string.CP_DefaultCameraDesc);
            case NebulaConfig.CAMERA_X -> getString(R.string.CP_CameraXDesc);
            case NebulaConfig.CAMERA_2 -> getString(R.string.CP_Camera2Desc);
            default -> getString(R.string.CP_SystemCameraDesc);
        };

        Spannable htmlParsed;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            htmlParsed = new SpannableString(Html.fromHtml(advise, Html.FROM_HTML_MODE_LEGACY));
        } else {
            htmlParsed = new SpannableString(Html.fromHtml(advise));
        }

        return ResourcesUtils.getUrlNoUnderlineText(htmlParsed);
    }

    private String getCameraXFpsRange() {
        return switch (app.nebulagram.messenger.NebulaConfig.cameraXFpsRange) {
            case NebulaConfig.CameraXFpsRange25to30 -> "25-30";
            case NebulaConfig.CameraXFpsRange30to30 -> "30-30";
            case NebulaConfig.CameraXFpsRange30to60 -> "30-60";
            case NebulaConfig.CameraXFpsRange60to60 -> "30-60";
            default -> getString(R.string.Default);
        };
    }

    private String getRoundCameraText() {
        switch (NebulaConfig.videoMessagesCamera) {
            case 1: return getString(R.string.NM_CAM_RearCamera);
            case 2: return getString(R.string.NM_CAM_AskCamera);
            default: return getString(R.string.NM_CAM_FrontCamera);
        }
    }

    private String getRoundVideoSizeText() {
        return switch (NebulaConfig.videoMessagesResolution) {
            case NebulaConfig.ROUND_SD -> getString(R.string.NM_CAM_RoundVideoSize_SD);
            case NebulaConfig.ROUND_STD -> getString(R.string.NM_CAM_RoundVideoSize_STD);
            case NebulaConfig.ROUND_HD, NebulaConfig.ROUND_FHD -> getString(R.string.NM_CAM_RoundVideoSize_HD);
            default -> getString(R.string.NM_CAM_RoundVideoSize_Auto);
        };
    }

    private String getRoundVideoBitrateText() {
        return NebulaConfig.videoMessagesBitrateKbps + " kbps";
    }
}
