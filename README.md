
# AdaptivePowerEngine A52s (Prototype)

This repository contains a prototype Android app that monitors battery/charging and uses a lightweight on-device adaptive model
to *decide* software-level actions. It is targeted at Samsung A52s hardware characteristics but **does not require root to run**.

## Features implemented in this prototype
- Foreground service that samples battery parameters (level, voltage, temperature, current where available).
- Local CSV logger of samples under app files directory.
- Tiny adaptive AI engine implemented in Kotlin (online linear model + heuristics) that predicts short-term drain.
- Control pipeline that applies software-level mitigations (reduce brightness hint, restrict background work, notify user).
- Hooks for smart-plug integration to allow physical on/off of charging (user must supply smart-plug credentials).
- UI to start/stop monitoring and view logs.

## Important limitations (read carefully)
- **Direct control of charging current/voltage or shutting down the device requires root or system-level privileges.**
  The app *cannot* safely change charger voltage/current from a normal app account. Kernel-level or vendor APIs are required for that.
- **Some telemetry (charging current, fine-grained power metrics) is device-dependent and may not be available on all Android versions.**
- The AI module is intentionally lightweight and interpretable; you can replace it with a TensorFlow Lite model later.
- For real charging optimization (changing charger behaviour) we recommend one of:
  1. Use a Wi‑Fi enabled smart-plug to control wall power (integrate its API; a stub is provided).
  2. Run on rooted device with a kernel/module that can control charging — **dangerous** and not recommended unless you fully understand the risks.

## Build
Open in Android Studio or run via command-line:

```bash
./gradlew assembleDebug
```

Generated APK will appear in `app/build/outputs/apk/`.

## Next steps I can do for you
- Implement true ML model (TensorFlow Lite) and include a sample `.tflite` trained from simulated data.
- Add UI screens for graphs, history export, comparison runs (AI vs baseline).
- Add smart‑plug driver integrations for common brands (TP-Link Kasa, Tuya, Shelly).
- Add optional root-mode controls (only if you supply rooted device instructions).

