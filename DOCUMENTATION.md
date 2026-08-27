# Lumi — Documentation technique

## Sommaire

1. [Vue d'ensemble](#1-vue-densemble)
2. [Stack technique](#2-stack-technique)
3. [Architecture](#3-architecture)
4. [Composants UI](#4-composants-ui)
5. [Écrans & fonctionnalités](#5-écrans--fonctionnalités)
6. [Données & persistance](#6-données--persistance)
7. [Intelligence artificielle](#7-intelligence-artificielle)
8. [Sécurité](#8-sécurité)
9. [RGPD & vie privée](#9-rgpd--vie-privée)
10. [Points d'amélioration](#10-points-damélioration)

---

## 1. Vue d'ensemble

**Lumi** est une application Android de bien-être mental. Elle permet à l'utilisateur de :

- Créer un profil et définir ses objectifs de bien-être
- Passer un quiz d'évaluation initiale (PHQ/GAD-inspiré, 7 questions)
- Recevoir un bilan personnalisé généré par IA
- Faire des check-ins émotionnels quotidiens avec retour de Lumi (IA)
- Pratiquer des exercices de bien-être (respiration, gratitude, Eisenhower)

| Propriété | Valeur |
|---|---|
| Nom | Lumi |
| Package | `com.example.moodjournal` |
| `minSdk` | 26 (Android 8.0) |
| `targetSdk` | 36 |
| Version | 1.0 (versionCode 1) |
| Langue | Français |

---

## 2. Stack technique

### Langage & plateforme

| Technologie | Usage |
|---|---|
| **Kotlin** | Langage principal |
| **Jetpack Compose** | UI déclarative (pas de XML layout) |
| **Material Design 3** | Système de design (composants, thème, couleurs) |

### Bibliothèques principales

| Bibliothèque | Version | Rôle |
|---|---|---|
| `androidx.navigation:navigation-compose` | 2.9.8 | Navigation entre écrans |
| `androidx.lifecycle:lifecycle-viewmodel-compose` | 2.10.0 | ViewModel Compose |
| `androidx.lifecycle:lifecycle-runtime-compose` | 2.10.0 | Cycle de vie Compose |
| `com.squareup.retrofit2:retrofit` | 2.9.0 | Client HTTP |
| `com.squareup.retrofit2:converter-gson` | 3.0.0 | Désérialisation JSON |
| `com.squareup.okhttp3:logging-interceptor` | 5.3.2 | Logs réseau (debug) |
| `org.jetbrains.kotlinx:kotlinx-coroutines-android` | 1.10.2 | Opérations asynchrones |
| `androidx.compose.material:material-icons-extended` | (BOM) | Icônes Material |

### Polices

| Police | Usage |
|---|---|
| **DM Serif Display** | Titres, headings (serif, doux) |
| **Geist** | Corps de texte, labels, UI (sans-serif) |

### API externe

| Service | Endpoint | Modèle |
|---|---|---|
| **Hugging Face Inference Router** | `https://router.huggingface.co/v1/chat/completions` | `meta-llama/Llama-3.1-8B-Instruct` |

---

## 3. Architecture

L'application suit le pattern **MVVM** (Model – View – ViewModel) recommandé par Google pour Android.

```
app/
├── data/
│   ├── local/
│   │   └── UserStorage.kt          # Persistance profil (SharedPreferences + Gson)
│   └── model/
│       ├── User.kt                  # Profil utilisateur
│       ├── CheckIn.kt               # Modèle check-in (non persisté)
│       ├── Gratitude.kt             # Modèle gratitude (non persisté)
│       └── Objectif.kt              # Objectif de bien-être
├── network/
│   ├── AIService.kt                 # Interface Retrofit (endpoint Hugging Face)
│   ├── AIModels.kt                  # Data classes requête/réponse API
│   └── AIRepository.kt             # Orchestration des appels IA
├── ui/
│   ├── theme/
│   │   ├── Color.kt                 # Palette complète (light + dark)
│   │   ├── Theme.kt                 # LumiTheme / LumiTheme (MaterialTheme)
│   │   └── Type.kt                  # Typographie (DM Serif + Geist)
│   ├── components/                  # Composants réutilisables
│   ├── navigation/
│   │   └── AppNavGraph.kt           # Graphe de navigation + bottom bar
│   ├── onboarding/                  # Écrans d'inscription et d'évaluation
│   ├── home/                        # Dashboard + check-in + réponse IA
│   ├── tools/                       # Exercices de bien-être
│   └── profile/                     # Profil utilisateur
└── viewmodel/
    └── LumiViewModel.kt      # État global de l'app
```

### Flux de données

```
Écran  →  événement utilisateur
       →  fonction ViewModel (updateCheckin, generateReport…)
       →  état Compose (mutableStateOf)
       →  recomposition automatique de l'UI
```

Les appels réseau (IA) sont effectués dans `viewModelScope.launch` via des coroutines, ce qui garantit leur annulation si le ViewModel est détruit.

---

## 4. Composants UI

### Composants partagés (`ui/components/`)

| Fichier | Composable(s) | Description |
|---|---|---|
| `Button.kt` | `FilledButton`, `FilledButtonBlock`, `SoftButton`, `GhostButton` | Boutons primaire, bloc, doux, fantôme |
| `TextField.kt` | `MoodTextField` | Champ de saisie stylisé (BasicTextField) |
| `ScreenHeader.kt` | `ScreenHeader` | En-tête avec bouton retour, eyebrow, slot droit |
| `Lumi.kt` | `Lumi` | Avatar Lumi (image vectorielle) |
| `GoalCard.kt` | `GoalCard` | Carte d'objectif sélectionnable |
| `Emotions.kt` | — | Liste `EMOTIONS`, helpers `emotionById`, `emotionColorById` |

### Système de design

**Palette de couleurs** (mode clair) :

| Token | Valeur | Usage |
|---|---|---|
| `Terracotta` | `#C9603F` | Couleur primaire, CTA |
| `Cream` | `#F4ECDF` | Fond principal |
| `Paper` | `#FAF5EC` | Fond des cartes |
| `Ink` | `#231F1B` | Texte primaire |
| `Ink2` | `#5C5448` | Texte secondaire |
| `Ink3` | `#8B8275` | Texte atténué, labels |
| `Sage` | `#5A6E55` | Accent calme/nature |
| `Marigold` | `#E0A437` | Accent joie/gratitude |
| `Blush` | `#C77383` | Accent anxiété/erreur |
| `Sky` | `#6B8DA8` | Accent fatigue/tristesse |

Un mode sombre complet est défini (`DarkColorScheme`).

**Typographie** :

| Style Compose | Police | Taille | Usage |
|---|---|---|---|
| `displayLarge` | DM Serif Display | 34 sp | Titres principaux (Splash) |
| `titleLarge` | DM Serif Display | 26 sp | Titres d'écran |
| `titleMedium` | DM Serif Display | 20 sp | Sous-titres, cartes |
| `bodyLarge` | Geist | 15 sp | Corps de texte |
| `bodyMedium` | Geist | 13.5 sp | Texte secondaire |
| `labelSmall` | Geist | 11 sp + 1.5 sp tracking | Labels "eyebrow" en petites capitales |

---

## 5. Écrans & fonctionnalités

### Flux d'onboarding (première ouverture)

```
Splash → Consent → Signup → Goals → Quiz → Report → Home
```

| Écran | Route | Description |
|---|---|---|
| **SplashScreen** | `splash` | Accueil avec Lumi et CTA |
| **ConsentScreen** | `consent` | Consentement RGPD explicite et granulaire (stockage local + traitement IA tiers), requis avant toute collecte |
| **SignupScreen** | `signup` | Saisie prénom, nom, email (validé), genre |
| **GoalsScreen** | `goals` | Sélection d'objectifs (grille 2×3) |
| **QuizScreen** | `quiz` | 7 questions bien-être (style GAD/PHQ), une à la fois avec barre de progression |
| **ReportScreen** | `report` | Bilan IA : score de bien-être, émotions dominantes, message Lumi |

### Écrans principaux (navigation bottom bar)

| Écran | Route | Onglet |
|---|---|---|
| **HomeScreen** | `home` | Accueil |
| **ToolsScreen** | `tools` | Outils |
| **ProfileScreen** | `profile` | Profil |

### Flux check-in quotidien

```
Home → CheckinScreen (3 étapes) → AIResponseScreen → Home
```

| Étape | Description |
|---|---|
| **Étape 1** | Grille 8 émotions (joie, calme, reconnaissant, fatigué, triste, anxieux, submergé, en colère) |
| **Étape 2** | Slider d'intensité 0–100 avec blob animé qui grandit selon la valeur |
| **Étape 3** | Zone de texte libre optionnelle |
| **Réponse IA** | Bulle utilisateur + message Lumi + citation inspirante |

### Exercices (Outils)

| Outil | Route | Description |
|---|---|---|
| **Respiration 4-7-8** | `breathing` | Orbe animé Sky, 4 cycles, phases inspiré/retenu/expiré avec décompte |
| **5 choses positives** | `gratitude` | 5 champs numérotés, confirmation avec Lumi, données non persistées |
| **Matrice Eisenhower** | `eisenhower` | (à implémenter) |

---

## 6. Données & persistance

### Ce qui est persisté

| Donnée | Stockage | Clé | Format |
|---|---|---|---|
| **Profil utilisateur** | `SharedPreferences` (`moodjournal`) | `users` | JSON (Gson) |

Le profil (`User`) contient : `nom`, `prenom`, `email`, `sexe`, `objectifs: List<String>`.

### Ce qui n'est pas persisté

| Donnée | Raison |
|---|---|
| Check-ins émotionnels | Choix délibéré — données sensibles, vie privée |
| Séances de gratitude | Idem |
| Réponses au quiz | Utilisées une seule fois pour générer le rapport |
| Réponses de l'IA | Éphémères, affichées une fois |

Les données de check-in et de gratitude existent uniquement en mémoire vive (`ViewModel`) le temps de la session. Elles disparaissent à la fermeture de l'application.

### Suppression des données

L'utilisateur peut supprimer son profil depuis **Profil → Supprimer mes données**, après confirmation via un `AlertDialog`. L'action appelle `userStorage.clearUser()` et redirige vers le Splash.

---

## 7. Intelligence artificielle

### Modèle utilisé

- **Modèle** : `meta-llama/Llama-3.1-8B-Instruct`
- **Fournisseur** : Hugging Face Inference Router
- **Protocole** : API compatible OpenAI (`/v1/chat/completions`)

### Deux appels IA

| Appel | Déclencheur | Prompt envoyé | Réponse attendue |
|---|---|---|---|
| `getAIReport` | Fin du quiz | Objectifs + réponses quiz (0–3) | Bilan 3–4 phrases + conseil |
| `getCheckinResponse` | Fin du check-in | Émotion + intensité + note libre | Retour 2–3 phrases + conseil |

### Clé API

La clé Hugging Face est stockée dans `local.properties` (`HF_API_KEY`) et injectée à la compilation via `BuildConfig.HF_API_KEY`. Elle n'est **jamais** en clair dans le code source.

```
# local.properties (non versionné)
HF_API_KEY=hf_xxxxxxxxxxxx
```

---

## 8. Sécurité

### Points positifs

| Mesure | Détail |
|---|---|
| **Clé API hors dépôt** | `local.properties` doit être dans `.gitignore` — la clé n'est jamais committée |
| **Pas de serveur propre** | Aucune donnée utilisateur ne transite vers un backend applicatif maîtrisé |
| **Données en mémoire** | Les données sensibles (émotions, check-ins) ne sont jamais écrites sur le disque |
| **Permission unique** | Seul `INTERNET` est déclaré dans le Manifest |
| **Validation email** | `android.util.Patterns.EMAIL_ADDRESS` côté client avant soumission |

### Points de vigilance

| Risque | Niveau | Recommandation |
|---|---|---|
| **Clé API dans l'APK** | Moyen | La clé est dans `BuildConfig` — extractible par décompilation. Passer par un proxy backend pour la production |
| **SharedPreferences non chiffré** | Faible | Pour une v1 locale c'est acceptable. En production, utiliser `EncryptedSharedPreferences` (Jetpack Security) |
| **Pas de certificate pinning** | Faible | Les appels Hugging Face ne sont pas épinglés. À considérer si l'app gère des données médicales |
| **`minifyEnabled false`** | Moyen | L'obfuscation ProGuard est désactivée en release. Activer R8 en production |
| **`allowBackup = true`** | Faible | Les SharedPreferences peuvent être sauvegardées via ADB. Passer à `false` ou configurer `data_extraction_rules.xml` |

---

## 9. RGPD & vie privée

### Données collectées

| Donnée | Catégorie RGPD | Base légale | Durée |
|---|---|---|---|
| Prénom, Nom | Données personnelles (Art. 4) | Consentement | Jusqu'à suppression |
| Adresse e-mail | Données personnelles | Consentement | Jusqu'à suppression |
| Genre | Donnée à caractère personnel | Consentement | Jusqu'à suppression |
| Objectifs de bien-être | Donnée potentiellement sensible (santé) | Consentement explicite | Jusqu'à suppression |
| Émotions / check-ins | **Donnée de santé (Art. 9)** | Non conservées | Uniquement en session |

### Ce qui n'est pas collecté

- Aucune donnée de localisation
- Aucun identifiant publicitaire (pas de SDK analytics, Firebase, etc.)
- Aucune donnée biométrique
- Les check-ins et la gratitude **ne quittent jamais l'appareil** (sauf les 2 phrases envoyées à l'IA pour générer la réponse)

### Données envoyées à Hugging Face

Lors d'un check-in, les éléments suivants sont transmis à l'API Hugging Face :

```
émotion (ex. "anxieux") + intensité (ex. 72) + note libre éventuelle
```

Ces données sont envoyées **sans identifiant utilisateur** (pas de nom, pas d'email). L'utilisateur est explicitement informé et donne son consentement à ce traitement par un tiers (Hugging Face / Meta LLaMA) dès l'onboarding, via le `ConsentScreen`. Le champ « note libre » est transmis tel quel et n'est pas filtré : l'écran de consentement invite l'utilisateur à ne pas y inscrire d'informations personnelles.

### Consentement (ConsentScreen)

Avant toute collecte, l'utilisateur passe par un écran de consentement granulaire qui exige **deux accords distincts** :

1. **Stockage local du profil** (prénom, nom, e-mail, genre) — uniquement sur l'appareil.
2. **Traitement par l'IA tierce** — objectifs et check-ins envoyés à Hugging Face / Meta Llama, sans nom ni e-mail.

Le bouton « J'accepte et je continue » reste désactivé tant que les deux cases ne sont pas cochées. Le consentement est persisté (`UserStorage.setConsentGiven`) et vérifié au démarrage : sans consentement, l'application redémarre sur l'onboarding. La suppression des données (`clearUser`) efface aussi le consentement, ce qui le redemande au prochain lancement.

### Droits de l'utilisateur (Art. 15–17 RGPD)

| Droit | Implémentation actuelle |
|---|---|
| **Droit d'accès** | Partiellement — le profil est visible dans l'écran Profil |
| **Droit à l'effacement** | Profil → "Supprimer mes données" → suppression immédiate |
| **Droit à la portabilité** | Profil → "Exporter mes données" → partage texte via share sheet |
| **Droit d'opposition** | Non applicable (pas de traitement automatisé persisté) |

### Recommandations pour une mise en production

1. ~~**Mention d'information** : afficher une notice RGPD au premier lancement~~ → **fait** (`ConsentScreen`)
2. ~~**Consentement explicite pour l'IA** : informer l'utilisateur que ses émotions sont envoyées à un modèle tiers~~ → **fait** (`ConsentScreen`)
3. **Politique de confidentialité** : page dédiée, accessible depuis le Profil (les lignes « Confidentialité » / « Conditions d'utilisation » du profil ne sont pas encore reliées à un contenu)
4. **Chiffrement** : utiliser `EncryptedSharedPreferences` pour les données du profil
5. **DPO** : désigner un délégué à la protection des données si l'app est distribuée publiquement
6. **Registre des traitements** : documenter les flux de données (app → Hugging Face)

---

## 10. Build & déploiement

### Générer un APK debug

Depuis la racine du projet :

```bash
./gradlew assembleDebug
```

Le fichier généré se trouve à :

```
app/build/outputs/apk/debug/app-debug.apk
```

Cet APK est signé avec la clé de debug Android, adapté aux tests. Il peut être installé directement sur un appareil physique ou un émulateur.

### Installer sur un appareil physique

1. Transférer `app-debug.apk` sur le téléphone (câble USB, Drive, email…)
2. Activer **Autoriser les sources inconnues** dans les paramètres de sécurité de l'appareil
3. Ouvrir le fichier `.apk` depuis le gestionnaire de fichiers → **Installer**

### Générer un APK release (production)

```bash
./gradlew assembleRelease
```

> Un keystore de signature est requis. Configurer `signingConfigs` dans `app/build.gradle` avant de lancer cette commande.

---

## 11. Points d'amélioration

### Technique

| Sujet | Priorité | Description |
|---|---|---|
| Proxy backend | Haute | Masquer la clé API derrière un serveur intermédiaire |
| `EncryptedSharedPreferences` | Moyenne | Chiffrer le profil stocké localement |
| ProGuard / R8 | Moyenne | Activer l'obfuscation en release |
| Tests unitaires | Moyenne | Couvrir le ViewModel et les helpers |
| Gestion d'erreur réseau | Haute | Afficher un message si l'API IA est indisponible |
| `allowBackup = false` | Faible | Désactiver la sauvegarde ADB du profil |

### Fonctionnel

| Sujet | Description |
|---|---|
| Matrice Eisenhower | Écran listé dans les Outils mais non implémenté |
| Mode sombre | Tokens définis, à tester sur un appareil en dark mode |
| Notifications | Rappel quotidien pour le check-in |
| Onboarding "connexion" | Le bouton "J'ai déjà un compte" sur le Splash n'est pas fonctionnel |
| Accessibilité | `contentDescription` à compléter sur les icônes et images |
