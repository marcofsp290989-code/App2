Adaptive Power Engine - Ready to build (skeleton)
================================================
Este repositório contém um projecto Android (Kotlin + Jetpack Compose) preparado para compilar em Codemagic ou Android Studio.

Instruções rápidas:
1) Coloca este código num repositório GitHub.
2) Liga o repositório ao Codemagic e executa a workflow `assembleDebug` (o codemagic.yaml incluido faz ./gradlew assembleDebug).
3) Se preferires compilar localmente, abre o projecto no Android Studio e compila normalmente.

Nota: Este é um skeleton funcional com o motor AI melhorado e UI. Algumas funcionalidades (controlo físico da corrente, cortar alimentação) requerem root ou hardware externo — estão documentadas no código.
