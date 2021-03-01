<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://docs.simplicite.io//logos/logo250.png)
* * *

`COMPAS_NRCO` module definition
===============================

Trigramme : COM

`AppComClientComClient` business object definition
--------------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

`ComACAppel` business object definition
---------------------------------------

Action commerciale de type appel

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

`ComACAutre` business object definition
---------------------------------------

Action commerciale de type vente autre

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

`ComACGMS` business object definition
-------------------------------------

Action commerciale de type Pack GMS

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

### Custom actions

* `ComACGMS_newPackMail`: ComACGMS_newPackMail

`ComACMer` business object definition
-------------------------------------

Action commerciale de type Merchandising

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

`ComACOPV` business object definition
-------------------------------------

Action commerciale de type Création / Mutation

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

### Custom actions

* `ComOPVConvetionQuotidienne`: 
* `ComACOPVSAP-Action`: 
* `COMACOPV_Convention_OPV`: Document de convention opv
* `ComPVgenererJoursSemaine`: 
* `ComACOPV_fiche_client`: Crée la fiche client

`ComACPBR` business object definition
-------------------------------------

Action Commerciale de type Pack Brocante

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

### Custom actions

* `ComACPBRSAP-Action`: 
* `ComBCPbr`: 

`ComACPNR` business object definition
-------------------------------------

Action commerciale de type PNR

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

### Custom actions

* `ComACPRN-accordPartnariat`: 
* `ComACPNRSAP-Action`: 

`ComACRDV` business object definition
-------------------------------------

Action commerciale de type rdv

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

`ComActionCommerciale` business object definition
-------------------------------------------------

Actions commerciales

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comACClientId` link to **`ComClient`**                      | id                                       |          | yes       |          | Clé étrangère identifiant un client                                              |
| _Ref. `comACClientId.comCliID`_                              | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |
| _Ref. `comACClientId.comCliRaisonSociale`_                   | _char(100)_                              |          |           |          | _Raison sociale_                                                                 |
| _Ref. `comACClientId.comCliSIRET`_                           | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `comACClientId.comCliRcs`_                             | _char(50)_                               |          |           |          | -                                                                                |
| _Ref. `comACClientId.comCliZone`_                            | _char(10)_                               |          |           |          | -                                                                                |
| `comACid`                                                    | char(40)                                 | yes*     |           |          | -                                                                                |
| `comACdatePrev`                                              | date                                     |          | yes       |          | Date prévue                                                                      |
| `comAClibelle`                                               | char(40)                                 | yes      | yes       |          | -                                                                                |
| `comACtype`                                                  | enum(7) using `COMACTYPE` list           | yes      | yes       |          | Type d'action commerciale                                                        |
| `comACcommentaire`                                           | html(1000)                               |          | yes       |          | Commentaire d'une action commerciale                                             |
| `comACEventId`                                               | char(40)                                 |          |           |          | -                                                                                |
| `comACsapID`                                                 | char(50)                                 |          |           |          | -                                                                                |
| `comACkey`                                                   | char(100)                                |          | yes       |          | -                                                                                |
| `comACAdresseId` link to **`ComAdresse`**                    | id                                       |          | yes       |          | Clé étrangère identifiant une adresse                                            |
| _Ref. `comACAdresseId.comAdrId`_                             | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comACAdresseId.comAdresseClientId`_                   | _id_                                     |          |           |          | -                                                                                |
| _Ref. `comAdresseClientId.comCliID`_                         | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |
| _Ref. `comACAdresseId.comAdrLibelle`_                        | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comACAdresseId.comAdrLigne1`_                         | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `comACAdresseId.comAdrCP`_                             | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comACAdresseId.comAdrVille`_                          | _char(40)_                               |          |           |          | -                                                                                |
| `comACContactId` link to **`ComContact`**                    | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comACContactId.comContactNom`_                        | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comACContactId.comContactPrenom`_                     | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comACContactId.comContactEmail`_                      | _email(100)_                             |          |           |          | -                                                                                |
| _Ref. `comACContactId.comContactTel`_                        | _phone(12)_                              |          |           |          | -                                                                                |
| `comACUserId` link to **`ComUser`**                          | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comACUserId.usr_last_name`_                           | _char(50)_                               |          |           | yes      | _Last name_                                                                      |
| _Ref. `comACUserId.usr_first_name`_                          | _char(50)_                               |          |           | yes      | _First name_                                                                     |
| `comACzoneLibelle`                                           | char(5)                                  |          | yes       |          | -                                                                                |
| `comPVnr`                                                    | boolean                                  |          | yes       |          | -                                                                                |
| `comPVcp`                                                    | boolean                                  |          | yes       |          | -                                                                                |
| `comPVnrd`                                                   | boolean                                  |          | yes       |          | -                                                                                |
| `comPVpd`                                                    | boolean                                  |          | yes       |          | -                                                                                |
| `comPVps`                                                    | boolean                                  |          | yes       |          | -                                                                                |
| `comPVqPs`                                                   | int(5)                                   |          | yes       |          | -                                                                                |
| `comPVportageDim`                                            | boolean using `COMPVPORTAGEDIM` list     |          | yes       |          | -                                                                                |
| `comPVpdQuant`                                               | int(5)                                   |          | yes       |          | Quantité portage dimanche                                                        |
| `comPVtypologie`                                             | enum(7) using `COMPVTYPO` list           |          | yes       |          | -                                                                                |
| `comPVtypeDepositaire`                                       | enum(7) using `COMPVTYPEDEPOSITAIRE` list |          | yes       |          | -                                                                                |
| `comPVproPresse`                                             | boolean                                  |          | yes       |          | -                                                                                |
| `comPVemplacement`                                           | enum(7) using `COMPVEMPLACEMENT` list    |          | yes       |          | -                                                                                |
| `comPVsurface`                                               | enum(7) using `COMPVSURFACE` list        |          | yes       |          | -                                                                                |
| `comPVfreq`                                                  | enum(7) using `COMPVFREQ` list           |          | yes       |          | -                                                                                |
| `comPVespaceDif`                                             | boolean                                  |          | yes       |          | -                                                                                |
| `comACMAid` link to **`ComMotifAchat`**                      | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comACMAid.comMAlibelle`_                              | _char(200)_                              |          |           |          | -                                                                                |
| `comMotif`                                                   | enum(7) using `COMMOTIF` list            |          | yes       |          | -                                                                                |
| `comPVaffichette`                                            | int(3)                                   |          | yes       |          | -                                                                                |
| `comPVpresGrand`                                             | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVpresMoyen`                                             | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVpresChaise`                                            | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVpanTab`                                                | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVpanDivers`                                             | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVstopTrottoir`                                          | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVcadreAffiche`                                          | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVenseigne`                                              | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVheureFermetureDef`                                     | time                                     |          | yes       |          | -                                                                                |
| `comPVfermetureMidi`                                         | boolean                                  |          | yes       |          | -                                                                                |
| `comPVheureFermetureDefAM`                                   | time                                     |          | yes       |          | -                                                                                |
| `comPVheureOuvertureDefPM`                                   | time                                     |          | yes       |          | -                                                                                |
| `comPVouvert`                                                | boolean                                  |          |           |          | -                                                                                |
| `comPVheureOuvertureDef`                                     | time                                     |          | yes       |          | -                                                                                |
| `comPVheureMax`                                              | time                                     |          | yes       |          | -                                                                                |
| `comPVjoursFermeture`                                        | multi(100) using `COMPVJOURSFERMETURE` list |          | yes       |          | -                                                                                |
| `comPVtypePaiement`                                          | enum(7) using `COMPVTYPEPAIEMENT` list   |          | yes       |          | -                                                                                |
| `comPVfactFreq`                                              | enum(7) using `COMPVFACTFREQ` list       |          | yes       |          | -                                                                                |
| `comPVfactFreqFact`                                          | enum(7) using `COMPVFACTFREQFACT` list   |          | yes       |          | -                                                                                |
| `comPVfactPayeurDiff`                                        | char(100)                                |          | yes       |          | -                                                                                |
| `comPVfactCommMag`                                           | int(2)                                   |          | yes       |          | -                                                                                |
| `comPVfactCommPort`                                          | float(10, 4)                             |          | yes       |          | -                                                                                |
| `comPVfactCommDim`                                           | float(10, 4)                             |          | yes       |          | -                                                                                |
| `comPVfactIban`                                              | regexp(27)                               |          | yes       |          | -                                                                                |
| `comACsignatureCli`                                          | char(100)                                |          | yes       |          | -                                                                                |
| `comPNRdateDebut`                                            | date                                     |          | yes       |          | -                                                                                |
| `comPNRdateFin`                                              | date                                     |          | yes       |          | -                                                                                |
| `comPNRintitule`                                             | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRbonEdition`                                           | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRbonRubrique`                                          | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRbonDate`                                              | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRdateTirage`                                           | date                                     |          | yes       |          | -                                                                                |
| `comPNRdateRemise`                                           | date                                     |          | yes       |          | -                                                                                |
| `comPNRdateAnnonce`                                          | date                                     |          | yes       |          | -                                                                                |
| `comPNRnbAffOpe`                                             | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRnbAffOffert`                                          | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRnbFlyersBulletin`                                     | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRnbFlyOffert`                                          | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRnbUrnes`                                              | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRcommentaire`                                          | text(300)                                |          | yes       |          | -                                                                                |
| `comPNRadrOrga`                                              | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrSiret`                                             | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrNomPrenom`                                         | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrService`                                           | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrAdresse`                                           | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrComp`                                              | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrCPville`                                           | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrtel`                                               | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRprixTtc`                                              | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRtauxRemise`                                           | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRtotalRemise`                                          | bigdec(10, 4)                            |          |           |          | -                                                                                |
| `comPNRfraisLocAdr`                                          | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisTraitFich`                                       | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisRealEncart`                                      | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisExped`                                           | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisDistribStreet`                                   | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisLocCar`                                          | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisAnmin`                                           | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisAutre`                                           | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisAutreNature`                                     | char(50)                                 |          | yes       |          | -                                                                                |
| `comPNRdateFacturation`                                      | enum(7) using `COMPNRDATEFACTURATION` list |          | yes       |          | -                                                                                |
| `comPNRenvoiFacture`                                         | enum(7) using `COMPNRENVOIFACTURE` list  |          | yes       |          | -                                                                                |
| `comPNRdelaiReglement`                                       | enum(7) using `COMPNRDELAIREGLEMENT` list |          | yes       |          | -                                                                                |
| `comPBnom`                                                   | char(40)                                 |          | yes       |          | -                                                                                |
| `comPBdateOpe`                                               | date                                     |          | yes       |          | -                                                                                |
| `comACstatut`                                                | enum(7) using `COMACSTATUT` list         | yes      | yes       |          | -                                                                                |
| `comACdatePrev`                                              | date                                     |          | yes       |          | Date prévue                                                                      |
| `comACheurePrev`                                             | time                                     |          | yes       |          | -                                                                                |
| `comACdateFin`                                               | date                                     |          | yes       |          | Date de fin                                                                      |
| `comACheureFinPrev`                                          | time                                     |          | yes       |          | -                                                                                |
| `comACajouterAgenda`                                         | boolean                                  |          | yes       |          | -                                                                                |
| `comACListDate`                                              | date                                     |          | yes       |          | -                                                                                |
| `comACListType`                                              | char(20)                                 |          | yes       |          | -                                                                                |
| `comACListVille`                                             | char(100)                                |          | yes       |          | -                                                                                |
| `comACListRS`                                                | char(100)                                |          | yes       |          | -                                                                                |
| `comACListHeure`                                             | time                                     |          | yes       |          | -                                                                                |
| `comACListStatut`                                            | char(20)                                 |          | yes       |          | -                                                                                |

### Lists

* `COMACTYPE`
    - `VIS` VIS
    - `APP` APP
    - `RDV` RDV
    - `OPV` OPV
    - `PNR` PNR
    - `PBR` PBR
    - `PGM` PGM
    - `VPR` VPR
    - `MER` Merchandising
    - `VAU` VAU
* `COMPVTYPO`
    - `1` ALIMENTATION
    - `2` BAR avec tabac
    - `3` BAR sans tabac
    - `4` PRESSE TABAC avec bar
    - `5` PRESSE TABAC sans bar
    - `6` ENSEIGNE CONCEPT (Mag presse)
    - `7` KIOSQUE
    - `8` STATION SERVICE
    - `9` BOULANGERIE
    - `10` PRESSE
    - `11` RAYON GMS
    - `12` CAISSE GMS
    - `13` PRESSE LIBRAIRIE PAPETERIE
    - `14` RESTAURANT
    - `15` PHARMACIE
    - `16` POSTE
    - `17` MARCHE
    - `18` AUTRES
* `COMPVTYPEDEPOSITAIRE`
    - `1` Exclusif NR
    - `2` Exclusif PQR
    - `3` Avec NMPP
    - `4` Exclusif NRD
    - `5` Exclusif Presse Dim.
    - `6` Exclusif TMV
* `COMPVEMPLACEMENT`
    - `1` GALERIE AVEC HYPER
    - `2` GALERIE AVEC SUPER
    - `3` GALERIE GSS
    - `4` GALERIE SUPERETTE
    - `5` DANS AEROPORT
    - `6` DANS GSS
    - `7` DANS LA RUE
    - `8` DANS CAMPING
    - `9` DANS HOTEL
    - `10` DANS UNIVERSITE
    - `11` DANS AUTRES LIEUX
    - `12` DANS GARE ROUTIERE
    - `13` DANS GARE SNCF
    - `14` DANS HYPER
    - `15` DANS STATION SERVICE
    - `16` DANS SUPERETTE
    - `17` DANS SUPERMARCHE
    - `18` DANS UN HOPITAL
    - `19` SUR AUTOROUTE
    - `20` INCONNU
* `COMPVSURFACE`
    - `1` 1 m²
    - `2` 2 - 5 m²
    - `3` 6 - 9 m²
    - `4` 10 - 19 m²
    - `5` 20 - 49 m²
    - `6` 50 - 99 m²
    - `7` 100 - 199 m²
    - `8` 200 - 399 m²
    - `9` 400 - 499 m²
    - `10` 500 - 749 m²
    - `11` 750 - 999 m²
    - `12` 1000 - 2999 m²
    - `13` 3000 - 4999 m²
    - `14` 5000 - 7499 m²
    - `15` 7500 - 9999 m²
    - `16` 10000 m² et plus
* `COMPVFREQ`
    - `NA` Aucune donnée
    - `1` 1 -  499 clients
    - `2` 500 -  999 clients
    - `3` 1000 - 1499 clients
    - `4` 2500 - 4999 clients
    - `5` 5000 - 7499 clients
    - `6` 7500 - 9999 clients
    - `7` 10000 clients et plus
* `COMMOTIF`
    - `100` Dépositaire
    - `101` Vente Privilege Paquets
    - `118` Pack Classique
    - `122` Vente Privilége Poste
    - `130` Pack Envoi Poste
    - `131` Pack Paquet sous film
    - `142` Vente privilège sous film sans fichier
* `COMPVJOURSFERMETURE`
    - `Lundi` Lundi
    - `Mardi` Mardi
    - `Mercredi` Mercredi
    - `Jeudi` Jeudi
    - `Vendredi` Vendredi
    - `Samedi` Samedi
    - `Dimanche` Dimanche
* `COMPVTYPEPAIEMENT`
    - `CHE` Chèque
    - `PRE` Prélèvement
* `COMPVFACTFREQ`
    - `REC` Récurrent
    - `UN` Unique
* `COMPVFACTFREQFACT`
    - `HEB` Hebdomadaire
    - `MEN` Mensuelle
* `COMPNRDATEFACTURATION`
    - `FMO` Fin de mois
    - `HEB` Hebdomadaire
* `COMPNRENVOIFACTURE`
    - `M1` 2ème semaine du mois M+1
    - `S1` Vendredi S+1
* `COMPNRDELAIREGLEMENT`
    - `REC` A réception de facture
    - `REG` Réglé ce jour
* `COMACSTATUT`
    - `AF` AF
    - `FA` FA
    - `AN` AN

`ComActionCommercialeHistoric` business object definition
---------------------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `row_ref_id` link to **`ComActionCommerciale`**              | id                                       | yes*     |           |          | -                                                                                |
| `row_idx`                                                    | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `created_by_hist`                                            | char(100)                                | yes*     |           |          | -                                                                                |
| `created_dt_hist`                                            | datetime                                 | yes*     |           |          | -                                                                                |
| `comACid`                                                    | char(40)                                 | yes*     |           |          | -                                                                                |
| `comACdatePrev`                                              | date                                     |          | yes       |          | Date prévue                                                                      |
| `comACClientId` link to **`ComClient`**                      | id                                       |          | yes       |          | Clé étrangère identifiant un client                                              |
| _Ref. `comACClientId.comCliID`_                              | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |
| _Ref. `comACClientId.comCliRaisonSociale`_                   | _char(100)_                              |          |           |          | _Raison sociale_                                                                 |
| `comAClibelle`                                               | char(40)                                 | yes      | yes       |          | -                                                                                |
| _Ref. `comACClientId.comCliSIRET`_                           | _char(20)_                               |          |           |          | -                                                                                |
| `comACtype`                                                  | enum(7) using `COMACTYPE` list           | yes      | yes       |          | Type d'action commerciale                                                        |
| _Ref. `comACClientId.comCliRcs`_                             | _char(50)_                               |          |           |          | -                                                                                |
| `comACstatut`                                                | enum(7) using `COMACSTATUT` list         | yes      | yes       |          | -                                                                                |
| _Ref. `comACClientId.comCliZone`_                            | _char(10)_                               |          |           |          | -                                                                                |
| `comACdatePrev`                                              | date                                     |          | yes       |          | Date prévue                                                                      |
| `comACheurePrev`                                             | time                                     |          | yes       |          | -                                                                                |
| `comACdateFin`                                               | date                                     |          | yes       |          | Date de fin                                                                      |
| `comACheureFinPrev`                                          | time                                     |          | yes       |          | -                                                                                |
| `comACUserId` link to **`ComUser`**                          | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comACUserId.usr_last_name`_                           | _char(50)_                               |          |           | yes      | _Last name_                                                                      |
| _Ref. `comACUserId.usr_first_name`_                          | _char(50)_                               |          |           | yes      | _First name_                                                                     |
| `comACajouterAgenda`                                         | boolean                                  |          | yes       |          | -                                                                                |
| `comACcommentaire`                                           | html(1000)                               |          | yes       |          | Commentaire d'une action commerciale                                             |
| `comACContactId` link to **`ComContact`**                    | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comACContactId.comContactNom`_                        | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comACContactId.comContactPrenom`_                     | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comACContactId.comContactEmail`_                      | _email(100)_                             |          |           |          | -                                                                                |
| _Ref. `comACContactId.comContactTel`_                        | _phone(12)_                              |          |           |          | -                                                                                |
| `comACAdresseId` link to **`ComAdresse`**                    | id                                       |          | yes       |          | Clé étrangère identifiant une adresse                                            |
| _Ref. `comACAdresseId.comAdrLibelle`_                        | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comACAdresseId.comAdrLigne1`_                         | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `comACAdresseId.comAdrCP`_                             | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comACAdresseId.comAdrVille`_                          | _char(40)_                               |          |           |          | -                                                                                |
| `comACEventId`                                               | char(40)                                 |          |           |          | -                                                                                |
| `comPVnr`                                                    | boolean                                  |          | yes       |          | -                                                                                |
| `comPVcp`                                                    | boolean                                  |          | yes       |          | -                                                                                |
| `comPVnrd`                                                   | boolean                                  |          | yes       |          | -                                                                                |
| `comPVpd`                                                    | boolean                                  |          | yes       |          | -                                                                                |
| `comPVps`                                                    | boolean                                  |          | yes       |          | -                                                                                |
| `comPVqPs`                                                   | int(5)                                   |          | yes       |          | -                                                                                |
| `comPVportageDim`                                            | boolean using `COMPVPORTAGEDIM` list     |          | yes       |          | -                                                                                |
| `comPVpdQuant`                                               | int(5)                                   |          | yes       |          | Quantité portage dimanche                                                        |
| `comPVtypologie`                                             | enum(7) using `COMPVTYPO` list           |          | yes       |          | -                                                                                |
| `comPVtypeDepositaire`                                       | enum(7) using `COMPVTYPEDEPOSITAIRE` list |          | yes       |          | -                                                                                |
| `comPVproPresse`                                             | boolean                                  |          | yes       |          | -                                                                                |
| `comPVemplacement`                                           | enum(7) using `COMPVEMPLACEMENT` list    |          | yes       |          | -                                                                                |
| `comPVsurface`                                               | enum(7) using `COMPVSURFACE` list        |          | yes       |          | -                                                                                |
| `comPVfreq`                                                  | enum(7) using `COMPVFREQ` list           |          | yes       |          | -                                                                                |
| `comPVespaceDif`                                             | boolean                                  |          | yes       |          | -                                                                                |
| `comPVaffichette`                                            | int(3)                                   |          | yes       |          | -                                                                                |
| `comPVpresGrand`                                             | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVpresMoyen`                                             | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVpresChaise`                                            | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVpanTab`                                                | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVpanDivers`                                             | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVstopTrottoir`                                          | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVcadreAffiche`                                          | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVenseigne`                                              | int(10)                                  |          | yes       |          | -                                                                                |
| `comPVheureFermetureDef`                                     | time                                     |          | yes       |          | -                                                                                |
| `comPVfermetureMidi`                                         | boolean                                  |          | yes       |          | -                                                                                |
| `comPVheureFermetureDefAM`                                   | time                                     |          | yes       |          | -                                                                                |
| `comPVheureOuvertureDefPM`                                   | time                                     |          | yes       |          | -                                                                                |
| `comPVouvert`                                                | boolean                                  |          |           |          | -                                                                                |
| `comPVheureOuvertureDef`                                     | time                                     |          | yes       |          | -                                                                                |
| `comPVtypePaiement`                                          | enum(7) using `COMPVTYPEPAIEMENT` list   |          | yes       |          | -                                                                                |
| `comPVfactFreq`                                              | enum(7) using `COMPVFACTFREQ` list       |          | yes       |          | -                                                                                |
| `comPVfactFreqFact`                                          | enum(7) using `COMPVFACTFREQFACT` list   |          | yes       |          | -                                                                                |
| `comPVfactPayeurDiff`                                        | char(100)                                |          | yes       |          | -                                                                                |
| `comPVfactCommMag`                                           | int(2)                                   |          | yes       |          | -                                                                                |
| `comPVfactCommPort`                                          | float(10, 4)                             |          | yes       |          | -                                                                                |
| `comPVfactCommDim`                                           | float(10, 4)                             |          | yes       |          | -                                                                                |
| `comPVfactIban`                                              | regexp(27)                               |          | yes       |          | -                                                                                |
| `comPNRdateDebut`                                            | date                                     |          | yes       |          | -                                                                                |
| `comPNRdateFin`                                              | date                                     |          | yes       |          | -                                                                                |
| `comPNRintitule`                                             | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRbonEdition`                                           | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRbonRubrique`                                          | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRbonDate`                                              | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRdateTirage`                                           | date                                     |          | yes       |          | -                                                                                |
| `comPNRdateRemise`                                           | date                                     |          | yes       |          | -                                                                                |
| `comPNRdateAnnonce`                                          | date                                     |          | yes       |          | -                                                                                |
| `comPNRadrOrga`                                              | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrSiret`                                             | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrNomPrenom`                                         | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrService`                                           | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrAdresse`                                           | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrComp`                                              | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrCPville`                                           | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRadrtel`                                               | char(40)                                 |          | yes       |          | -                                                                                |
| `comPNRprixTtc`                                              | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRtauxRemise`                                           | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRtotalRemise`                                          | bigdec(10, 4)                            |          |           |          | -                                                                                |
| `comPNRfraisLocAdr`                                          | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisTraitFich`                                       | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisRealEncart`                                      | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisExped`                                           | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisDistribStreet`                                   | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisLocCar`                                          | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisAnmin`                                           | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisAutre`                                           | bigdec(10, 4)                            |          | yes       |          | -                                                                                |
| `comPNRfraisAutreNature`                                     | char(50)                                 |          | yes       |          | -                                                                                |
| `comPNRdateFacturation`                                      | enum(7) using `COMPNRDATEFACTURATION` list |          | yes       |          | -                                                                                |
| `comPNRenvoiFacture`                                         | enum(7) using `COMPNRENVOIFACTURE` list  |          | yes       |          | -                                                                                |
| `comPNRdelaiReglement`                                       | enum(7) using `COMPNRDELAIREGLEMENT` list |          | yes       |          | -                                                                                |
| `comPNRnbAffOpe`                                             | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRnbAffOffert`                                          | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRnbFlyersBulletin`                                     | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRnbFlyOffert`                                          | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRnbUrnes`                                              | int(10)                                  |          | yes       |          | -                                                                                |
| `comPNRcommentaire`                                          | text(300)                                |          | yes       |          | -                                                                                |
| `comPBnom`                                                   | char(40)                                 |          | yes       |          | -                                                                                |
| `comPBdateOpe`                                               | date                                     |          | yes       |          | -                                                                                |
| `comACsignatureCli`                                          | char(100)                                |          | yes       |          | -                                                                                |
| `comPVheureMax`                                              | time                                     |          | yes       |          | -                                                                                |
| `comPVjoursFermeture`                                        | multi(100) using `COMPVJOURSFERMETURE` list |          | yes       |          | -                                                                                |
| `comACMAid` link to **`ComMotifAchat`**                      | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comACMAid.comMAlibelle`_                              | _char(200)_                              |          |           |          | -                                                                                |
| `comACzoneLibelle`                                           | char(5)                                  |          | yes       |          | -                                                                                |
| `comACsapID`                                                 | char(50)                                 |          |           |          | -                                                                                |
| `comMotif`                                                   | enum(7) using `COMMOTIF` list            |          | yes       |          | -                                                                                |
| `comACListDate`                                              | date                                     |          | yes       |          | -                                                                                |
| `comACListType`                                              | char(20)                                 |          | yes       |          | -                                                                                |
| `comACListVille`                                             | char(100)                                |          | yes       |          | -                                                                                |
| `comACListRS`                                                | char(100)                                |          | yes       |          | -                                                                                |
| `comACListHeure`                                             | time                                     |          | yes       |          | -                                                                                |
| `comACListStatut`                                            | char(20)                                 |          | yes       |          | -                                                                                |
| `comACkey`                                                   | char(100)                                |          | yes       |          | -                                                                                |

### Lists

* `COMACTYPE`
    - `VIS` VIS
    - `APP` APP
    - `RDV` RDV
    - `OPV` OPV
    - `PNR` PNR
    - `PBR` PBR
    - `PGM` PGM
    - `VPR` VPR
    - `MER` Merchandising
    - `VAU` VAU
* `COMACSTATUT`
    - `AF` AF
    - `FA` FA
    - `AN` AN
* `COMPVTYPO`
    - `1` ALIMENTATION
    - `2` BAR avec tabac
    - `3` BAR sans tabac
    - `4` PRESSE TABAC avec bar
    - `5` PRESSE TABAC sans bar
    - `6` ENSEIGNE CONCEPT (Mag presse)
    - `7` KIOSQUE
    - `8` STATION SERVICE
    - `9` BOULANGERIE
    - `10` PRESSE
    - `11` RAYON GMS
    - `12` CAISSE GMS
    - `13` PRESSE LIBRAIRIE PAPETERIE
    - `14` RESTAURANT
    - `15` PHARMACIE
    - `16` POSTE
    - `17` MARCHE
    - `18` AUTRES
* `COMPVTYPEDEPOSITAIRE`
    - `1` Exclusif NR
    - `2` Exclusif PQR
    - `3` Avec NMPP
    - `4` Exclusif NRD
    - `5` Exclusif Presse Dim.
    - `6` Exclusif TMV
* `COMPVEMPLACEMENT`
    - `1` GALERIE AVEC HYPER
    - `2` GALERIE AVEC SUPER
    - `3` GALERIE GSS
    - `4` GALERIE SUPERETTE
    - `5` DANS AEROPORT
    - `6` DANS GSS
    - `7` DANS LA RUE
    - `8` DANS CAMPING
    - `9` DANS HOTEL
    - `10` DANS UNIVERSITE
    - `11` DANS AUTRES LIEUX
    - `12` DANS GARE ROUTIERE
    - `13` DANS GARE SNCF
    - `14` DANS HYPER
    - `15` DANS STATION SERVICE
    - `16` DANS SUPERETTE
    - `17` DANS SUPERMARCHE
    - `18` DANS UN HOPITAL
    - `19` SUR AUTOROUTE
    - `20` INCONNU
* `COMPVSURFACE`
    - `1` 1 m²
    - `2` 2 - 5 m²
    - `3` 6 - 9 m²
    - `4` 10 - 19 m²
    - `5` 20 - 49 m²
    - `6` 50 - 99 m²
    - `7` 100 - 199 m²
    - `8` 200 - 399 m²
    - `9` 400 - 499 m²
    - `10` 500 - 749 m²
    - `11` 750 - 999 m²
    - `12` 1000 - 2999 m²
    - `13` 3000 - 4999 m²
    - `14` 5000 - 7499 m²
    - `15` 7500 - 9999 m²
    - `16` 10000 m² et plus
* `COMPVFREQ`
    - `NA` Aucune donnée
    - `1` 1 -  499 clients
    - `2` 500 -  999 clients
    - `3` 1000 - 1499 clients
    - `4` 2500 - 4999 clients
    - `5` 5000 - 7499 clients
    - `6` 7500 - 9999 clients
    - `7` 10000 clients et plus
* `COMPVTYPEPAIEMENT`
    - `CHE` Chèque
    - `PRE` Prélèvement
* `COMPVFACTFREQ`
    - `REC` Récurrent
    - `UN` Unique
* `COMPVFACTFREQFACT`
    - `HEB` Hebdomadaire
    - `MEN` Mensuelle
* `COMPNRDATEFACTURATION`
    - `FMO` Fin de mois
    - `HEB` Hebdomadaire
* `COMPNRENVOIFACTURE`
    - `M1` 2ème semaine du mois M+1
    - `S1` Vendredi S+1
* `COMPNRDELAIREGLEMENT`
    - `REC` A réception de facture
    - `REG` Réglé ce jour
* `COMPVJOURSFERMETURE`
    - `Lundi` Lundi
    - `Mardi` Mardi
    - `Mercredi` Mercredi
    - `Jeudi` Jeudi
    - `Vendredi` Vendredi
    - `Samedi` Samedi
    - `Dimanche` Dimanche
* `COMMOTIF`
    - `100` Dépositaire
    - `101` Vente Privilege Paquets
    - `118` Pack Classique
    - `122` Vente Privilége Poste
    - `130` Pack Envoi Poste
    - `131` Pack Paquet sous film
    - `142` Vente privilège sous film sans fichier

`ComACVisite` business object definition
----------------------------------------

Action commerciale de type visite

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

`ComACVPriv` business object definition
---------------------------------------

Action commerciale de type Vente privilège

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

### Custom actions

* `ComACVPrivSAP-Action`: 
* `ComBCVpriv`: 

`ComAdresse` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comAdrLibelle`                                              | char(40)                                 |          | yes       |          | -                                                                                |
| `comAdrCat`                                                  | enum(7) using `COMADRTYPE` list          |          | yes       |          | -                                                                                |
| `comAdrType`                                                 | enum(7) using `COMADRCAT` list           | yes      | yes       |          | -                                                                                |
| `comAdresseAgenceId` link to **`ComAgences`**                | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comAdresseAgenceId.comAgenceNom`_                     | _char(255)_                              |          |           |          | -                                                                                |
| `comAdrLigne1`                                               | char(100)                                | yes      | yes       |          | -                                                                                |
| `comAdrLigne2`                                               | char(40)                                 |          | yes       |          | -                                                                                |
| `comAdrLigne3`                                               | char(40)                                 |          | yes       |          | -                                                                                |
| `comAdrCP`                                                   | char(40)                                 |          | yes       |          | -                                                                                |
| `comAdrVille`                                                | char(40)                                 |          | yes       |          | -                                                                                |
| `comAdrPays`                                                 | char(40)                                 |          | yes       |          | -                                                                                |
| `comAdresseClientId` link to **`ComClient`**                 | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `comAdresseClientId.comCliID`_                         | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |
| _Ref. `comAdresseClientId.comCliRaisonSociale`_              | _char(100)_                              |          |           |          | _Raison sociale_                                                                 |
| `comAdrCoords`                                               | geocoords                                |          | yes       |          | -                                                                                |
| `comAdrId`                                                   | char(40)                                 | yes*     |           |          | -                                                                                |
| `comAdrCodeINSEE`                                            | char(100)                                |          | yes       |          | -                                                                                |
| `comAdrNumVoie`                                              | char(10)                                 |          | yes       |          | -                                                                                |
| `comAdrLabelVoie`                                            | char(200)                                |          | yes       |          | -                                                                                |

### Lists

* `COMADRTYPE`
    - `LIV` Livraison
    - `BP` BP
* `COMADRCAT`
    - `PRIN` Principale
    - `SEC` Secondaire

`ComAgences` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comAgenceNom`                                               | char(255)                                | yes*     | yes       |          | -                                                                                |
| `comAgenceOrdre`                                             | int(11)                                  | yes      | yes       |          | -                                                                                |

`ComClient` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comCliID`                                                   | char(40)                                 | yes*     |           |          | Identifiant unique du client                                                     |
| `comCliRaisonSociale`                                        | char(100)                                | yes      | yes       |          | Raison sociale                                                                   |
| `comCliSAPid`                                                | char(40)                                 |          |           |          | -                                                                                |
| `comCliNature`                                               | enum(7) using `COMCLINATURE` list        | yes      | yes       |          | Nature                                                                           |
| `comCliType`                                                 | enum(7) using `COMCLITYPE` list          |          | yes       |          | Type de client                                                                   |
| `comCliCommentaire`                                          | text(1000)                               |          | yes       |          | -                                                                                |
| `comCliCat`                                                  | enum(7) using `COMCLICAT` list           |          | yes       |          | -                                                                                |
| `comCliZone`                                                 | char(10)                                 |          | yes       |          | -                                                                                |
| `comCliSIRET`                                                | char(20)                                 |          | yes       |          | -                                                                                |
| `comCliRcs`                                                  | char(50)                                 |          | yes       |          | -                                                                                |
| `comCliChorus`                                               | boolean                                  |          | yes       |          | -                                                                                |
| `comCliGeoCoords`                                            | geocoords                                |          | yes       |          | -                                                                                |
| `comCliGeoLibelle`                                           | char(40)                                 |          |           |          | Libellé de l'adresse principale                                                  |
| `comCliGeoLigne1`                                            | char(40)                                 |          |           |          | Ligne 1 de l'adresse principale                                                  |
| `comCliGeoVille`                                             | char(40)                                 |          |           |          | -                                                                                |

### Lists

* `COMCLINATURE`
    - `PRO` Prospect
    - `CLI` Client
* `COMCLITYPE`
    - `DEP` Dépositaire
    - `DIF` Diffuseur
* `COMCLICAT`
    - `VN` Vente au n°
    - `ABO` Abonné

### Custom actions

* `ComClientLocaliser`: Afficher le client sur une carte

`ComClientClient` business object definition
--------------------------------------------

Relation entre dépositaire / distributeur

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comClientDepositaireId` link to **`ComClient`**             | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `comClientDepositaireId.comCliID`_                     | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |
| _Ref. `comClientDepositaireId.comCliRaisonSociale`_          | _char(100)_                              |          |           |          | _Raison sociale_                                                                 |
| `comClientDiffuseurId` link to **`ComClient`**               | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `comClientDiffuseurId.comCliID`_                       | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |
| _Ref. `comClientDiffuseurId.comCliRaisonSociale`_            | _char(100)_                              |          |           |          | _Raison sociale_                                                                 |

`ComCommandeJournaux` business object definition
------------------------------------------------

Commande de journaux associés à un pack NR ou une vente privilège

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comComId`                                                   | char(40)                                 | yes*     |           |          | -                                                                                |
| `comComDateParution`                                         | date                                     | yes      | yes       |          | -                                                                                |
| `comComEdition`                                              | enum(7) using `COMEDEDITION` list        |          | yes       |          | -                                                                                |
| `comComQuantite`                                             | int(10)                                  |          | yes       |          | -                                                                                |
| `comComTypeLivraison`                                        | enum(7) using `COMPNRTYPELIVRAISON` list |          | yes       |          | -                                                                                |
| `comComsiPaquet`                                             | char(40)                                 |          | yes       |          | -                                                                                |
| `comComEncart`                                               | boolean                                  |          | yes       |          | -                                                                                |
| `comComsiEncart`                                             | char(200)                                |          | yes       |          | -                                                                                |
| `comComTvMag`                                                | boolean                                  |          | yes       |          | -                                                                                |
| `comComsupplement`                                           | boolean                                  |          | yes       |          | -                                                                                |
| `comComFichier`                                              | enum(7) using `COMPNRFICHIER` list       |          | yes       |          | -                                                                                |
| `comComSiFichier`                                            | char(40)                                 |          | yes       |          | -                                                                                |
| `comComDelaiLivraison`                                       | enum(7) using `COMPNRDELAILIVRAISON` list |          | yes       |          | -                                                                                |
| `comComRoutage`                                              | char(40)                                 |          | yes       |          | -                                                                                |
| `comComAdresse`                                              | char(200)                                |          | yes       |          | -                                                                                |
| `comComACid` link to **`ComActionCommerciale`**              | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comComACid.comACid`_                                  | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comComACid.comACClientId`_                            | _id_                                     |          |           |          | _Clé étrangère identifiant un client_                                            |
| _Ref. `comACClientId.comCliID`_                              | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |
| _Ref. `comComACid.comAClibelle`_                             | _char(40)_                               |          |           |          | -                                                                                |
| `comMotif`                                                   | enum(7) using `COMMOTIF` list            |          | yes       |          | -                                                                                |
| `comMotif`                                                   | enum(7) using `COMMOTIF` list            |          | yes       |          | -                                                                                |
| `comComObs`                                                  | char(100)                                |          | yes       |          | -                                                                                |

### Lists

* `COMEDEDITION`
    - `36` Indre
    - `37` Indre et Loire
    - `41` Loir et Cher
    - `79` Deux Sèvres
    - `86` Vienne
    - `CP` Centre Presse
* `COMPNRTYPELIVRAISON`
    - `PAQ` Paquet
    - `EPO` Envoi Postal
    - `SFP` Sous film en paquets
* `COMPNRFICHIER`
    - `AUC` Aucun
    - `CLI` Client
    - `NR` NR
    - `NRC` NR et clients
* `COMPNRDELAILIVRAISON`
    - `J` J
    - `J1` J + 1
* `COMMOTIF`
    - `100` Dépositaire
    - `101` Vente Privilege Paquets
    - `118` Pack Classique
    - `122` Vente Privilége Poste
    - `130` Pack Envoi Poste
    - `131` Pack Paquet sous film
    - `142` Vente privilège sous film sans fichier

`ComContact` business object definition
---------------------------------------

Contact associé à un client

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comContactID`                                               | char(40)                                 | yes*     | yes       |          | Identifiant unique du contact                                                    |
| `comContactClientId` link to **`ComClient`**                 | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comContactClientId.comCliID`_                         | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |
| _Ref. `comContactClientId.comCliRaisonSociale`_              | _char(100)_                              |          |           |          | _Raison sociale_                                                                 |
| `comContactStatut`                                           | enum(7) using `COMCONTACTSTATUT` list    | yes      | yes       |          | -                                                                                |
| `comContactPrincipal`                                        | boolean                                  |          | yes       |          | -                                                                                |
| `comContactCivilite`                                         | enum(7) using `COMCONTACTCIVILITE` list  | yes      | yes       |          | -                                                                                |
| `comContactNom`                                              | char(40)                                 | yes*     | yes       |          | -                                                                                |
| `comContactPrenom`                                           | char(40)                                 |          | yes       |          | -                                                                                |
| `comContactFonction`                                         | char(40)                                 |          | yes       |          | -                                                                                |
| `comContactTel`                                              | phone(12)                                |          | yes       |          | -                                                                                |
| `comContactEmail`                                            | email(100)                               |          | yes       |          | -                                                                                |

### Lists

* `COMCONTACTSTATUT`
    - `AC` Actif
    - `NA` Non actif
* `COMCONTACTCIVILITE`
    - `Mme` Madame
    - `M` Monsieur

`ComDotation` business object definition
----------------------------------------

Dotations associées à un pack NR

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comDotId`                                                   | char(10)                                 | yes*     |           |          | Identifiant unique                                                               |
| `comDotLotNum`                                               | int(10)                                  |          | yes       |          | -                                                                                |
| `comDotLotNom`                                               | char(40)                                 |          | yes       |          | -                                                                                |
| `comDotLotPrix`                                              | int(10)                                  |          | yes       |          | -                                                                                |
| `comDotACid` link to **`ComActionCommerciale`**              | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `comDotACid.comACid`_                                  | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comDotACid.comACClientId`_                            | _id_                                     |          |           |          | _Clé étrangère identifiant un client_                                            |
| _Ref. `comACClientId.comCliID`_                              | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |

`ComEdition` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comEdIdentifiant`                                           | char(40)                                 | yes*     |           |          | -                                                                                |
| `comEdType`                                                  | enum(7) using `COMEDTYPE` list           | yes      | yes       |          | -                                                                                |
| `comEdSupport`                                               | enum(7) using `COMEDSUPPORT` list        | yes      | yes       |          | -                                                                                |
| `comEdEditionNR`                                             | enum(7) using `COMEDEDITION` list        |          | yes       |          | -                                                                                |
| `comEdEditionCP`                                             | enum(7) using `COMEDEDITIONCP` list      |          | yes       |          | -                                                                                |
| `comEdOffre`                                                 | enum(7) using `COMEDOFFRE` list          |          | yes       |          | -                                                                                |
| `comEdLundi`                                                 | int(5)                                   |          | yes       |          | -                                                                                |
| `comEdMardi`                                                 | int(5)                                   |          | yes       |          | -                                                                                |
| `comEdMercredi`                                              | int(5)                                   |          | yes       |          | -                                                                                |
| `comEdJeudi`                                                 | int(5)                                   |          | yes       |          | -                                                                                |
| `comEdVendredi`                                              | int(5)                                   |          | yes       |          | -                                                                                |
| `comEdSamedi`                                                | int(5)                                   |          | yes       |          | -                                                                                |
| `comEdDimanche`                                              | int(5)                                   |          | yes       |          | -                                                                                |
| `comEdDateDebut`                                             | date                                     |          | yes       |          | -                                                                                |
| `comEdACid` link to **`ComActionCommerciale`**               | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comEdACid.comACid`_                                   | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comEdACid.comACClientId`_                             | _id_                                     |          |           |          | _Clé étrangère identifiant un client_                                            |
| _Ref. `comACClientId.comCliID`_                              | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |

### Lists

* `COMEDTYPE`
    - `PRI` Principale
    - `SEC` Secondaire
* `COMEDSUPPORT`
    - `NR` Nouvelle République
    - `CP` Centre Presse
* `COMEDEDITION`
    - `36` Indre
    - `37` Indre et Loire
    - `41` Loir et Cher
    - `79` Deux Sèvres
    - `86` Vienne
    - `CP` Centre Presse
* `COMEDEDITIONCP`
    - `CP` Centre Presse
* `COMEDOFFRE`
    - `01` 01

`ComJourSemaine` business object definition
-------------------------------------------

Jours de la semaine

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comJSnom`                                                   | char(40)                                 | yes*     | yes       |          | -                                                                                |
| `comJSouvert`                                                | boolean                                  |          | yes       |          | -                                                                                |
| `comJSheureOuverture`                                        | time                                     |          | yes       |          | -                                                                                |
| `comJSheureFermetureMidi`                                    | time                                     |          | yes       |          | -                                                                                |
| `comJSheureOuvertureMidi`                                    | time                                     |          | yes       |          | -                                                                                |
| `comJSheureFermeture`                                        | time                                     |          | yes       |          | -                                                                                |
| `comJSACid` link to **`ComActionCommerciale`**               | id                                       | *        | yes       |          | -                                                                                |
| _Ref. `comJSACid.comACid`_                                   | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comJSACid.comACClientId`_                             | _id_                                     |          |           |          | _Clé étrangère identifiant un client_                                            |
| _Ref. `comACClientId.comCliID`_                              | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |

`ComMotifAchat` business object definition
------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comMAcode`                                                  | char(11)                                 |          | yes       |          | -                                                                                |
| `comMAlibelle`                                               | char(200)                                | yes*     | yes       |          | -                                                                                |

`ComPackBrocante` business object definition
--------------------------------------------

Action commerciale de type Pack Brocante

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comPBnom`                                                   | char(40)                                 |          | yes       |          | -                                                                                |

`ComPieceJointe` business object definition
-------------------------------------------

Pièces jointes pouvant être liées à un client ou une action commerciale

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comPJnom`                                                   | char(40)                                 |          | yes       |          | -                                                                                |
| `comPJID`                                                    | char(40)                                 | yes*     |           |          | Identifiant unique de la pièce jointe                                            |
| `comPJfichier`                                               | document                                 |          | yes       |          | -                                                                                |
| `comPJdescription`                                           | html(1000)                               |          | yes       |          | -                                                                                |
| `comPJClientId` link to **`ComClient`**                      | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comPJClientId.comCliID`_                              | _char(40)_                               |          |           |          | _Identifiant unique du client_                                                   |
| _Ref. `comPJClientId.comCliRaisonSociale`_                   | _char(100)_                              |          |           |          | _Raison sociale_                                                                 |
| `comPJAdresseId` link to **`ComAdresse`**                    | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comPJAdresseId.comAdrLibelle`_                        | _char(40)_                               |          |           |          | -                                                                                |
| `comPJContactId` link to **`ComContact`**                    | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comPJContactId.comContactID`_                         | _char(40)_                               |          |           |          | _Identifiant unique du contact_                                                  |
| _Ref. `comPJContactId.comContactNom`_                        | _char(40)_                               |          |           |          | -                                                                                |
| _Ref. `comPJContactId.comContactPrenom`_                     | _char(40)_                               |          |           |          | -                                                                                |
| `comPJaCId` link to **`ComActionCommerciale`**               | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comPJaCId.comAClibelle`_                              | _char(40)_                               |          |           |          | -                                                                                |

`ComPNR` business object definition
-----------------------------------

Pack NR

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comPNRidentifiant`                                          | char(40)                                 | yes*     |           |          | Identifant unique du PNR                                                         |

`ComUser` business object definition
------------------------------------

Utilisateurs de l'outil

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comUserZoneUserId` link to **`ComUserZone`**                | id                                       |          | yes       |          | -                                                                                |
| _Ref. `comUserZoneUserId.comUserZoneLibelle`_                | _char(5)_                                |          |           |          | -                                                                                |

`ComUserObjectifs` business object definition
---------------------------------------------

Objectifs

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comUserObjUserId` link to **`ComUser`**                     | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `comUserObjUserId.usr_login`_                          | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| _Ref. `comUserObjUserId.usr_first_name`_                     | _char(50)_                               |          |           | yes      | _First name_                                                                     |
| _Ref. `comUserObjUserId.usr_last_name`_                      | _char(50)_                               |          |           | yes      | _Last name_                                                                      |
| _Ref. `comUserObjUserId.comUserZoneUserId`_                  | _id_                                     |          |           |          | -                                                                                |
| _Ref. `comUserZoneUserId.comUserZoneLibelle`_                | _char(5)_                                |          |           |          | -                                                                                |
| `comUOmois`                                                  | enum(7) using `COMUOMOIS` list           | yes*     | yes       |          | -                                                                                |
| `comObjYear`                                                 | enum(7) using `COMOBJYEAR` list          | yes*     | yes       |          | -                                                                                |
| `comObjACRes`                                                | int(5)                                   |          |           |          | -                                                                                |
| `comObjAppObj`                                               | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjAppRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjVisRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjRdvObj`                                               | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjRdvRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjRESObj`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjRESRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjANIMObj`                                              | int(5)                                   |          |           |          | -                                                                                |
| `comObjANIMRes`                                              | int(5)                                   |          |           |          | -                                                                                |
| `comObjCreaObj`                                              | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjCreaRes`                                              | int(5)                                   |          |           |          | -                                                                                |
| `comObjMerchObj`                                             | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjMerchRes`                                             | int(5)                                   |          |           |          | -                                                                                |
| `comObjGmsObj`                                               | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjGmsRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjACObj`                                                | int(5)                                   |          |           |          | -                                                                                |
| `comObjectifsVentesMagObj`                                   | int(5)                                   |          |           |          | -                                                                                |
| `comObjectifsVentesMagRes`                                   | int(5)                                   |          |           |          | -                                                                                |
| `comObjVmagPnrObj`                                           | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVmagPnrRes`                                           | int(5)                                   |          |           |          | -                                                                                |
| `comObjVmagPnrdObj`                                          | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVmagPnrdRes`                                          | int(5)                                   |          |           |          | -                                                                                |
| `comObjVprivPnrObj`                                          | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVprivPnrRes`                                          | int(5)                                   |          |           |          | -                                                                                |
| `comObjVprivPnrdObj`                                         | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVprivPnrdRes`                                         | int(5)                                   |          |           |          | -                                                                                |
| `comObjVdivCAObj`                                            | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVdivCARes`                                            | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjectifsVentesPrivObj`                                  | int(5)                                   |          |           |          | -                                                                                |
| `comObjectifsVentesPrivRes`                                  | int(5)                                   |          |           |          | -                                                                                |
| `comObjectifsVentesDivObj`                                   | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjectifsVentesDivRes`                                   | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVprivCPObj`                                           | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVprivCPRes`                                           | int(5)                                   |          |           |          | -                                                                                |
| `comObjVmagCPObj`                                            | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVmagCPRes`                                            | int(5)                                   |          |           |          | -                                                                                |

### Lists

* `COMUOMOIS`
    - `1` Janvier
    - `2` Février
    - `3` Mars
    - `4` Avril
    - `5` Mai
    - `6` Juin
    - `7` Juillet
    - `8` Août
    - `9` Septembre
    - `10` Octobre
    - `11` Novembre
    - `12` Décembre
* `COMOBJYEAR`
    - `2018` 2018
    - `2019` 2019
    - `2020` 2020
    - `2021` 2021
    - `2022` 2022

`ComUserObjectifsHistoric` business object definition
-----------------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `row_ref_id` link to **`ComUserObjectifs`**                  | id                                       | yes*     |           |          | -                                                                                |
| `row_idx`                                                    | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `created_by_hist`                                            | char(100)                                | yes*     |           |          | -                                                                                |
| `created_dt_hist`                                            | datetime                                 | yes*     |           |          | -                                                                                |
| `comUserObjUserId` link to **`ComUser`**                     | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `comUserObjUserId.usr_login`_                          | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| _Ref. `comUserObjUserId.usr_first_name`_                     | _char(50)_                               |          |           | yes      | _First name_                                                                     |
| _Ref. `comUserObjUserId.usr_last_name`_                      | _char(50)_                               |          |           | yes      | _Last name_                                                                      |
| _Ref. `comUserObjUserId.comUserZoneUserId`_                  | _id_                                     |          |           |          | -                                                                                |
| _Ref. `comUserZoneUserId.comUserZoneLibelle`_                | _char(5)_                                |          |           |          | -                                                                                |
| `comUOmois`                                                  | enum(7) using `COMUOMOIS` list           | yes*     | yes       |          | -                                                                                |
| `comObjYear`                                                 | enum(7) using `COMOBJYEAR` list          | yes*     | yes       |          | -                                                                                |
| `comObjACRes`                                                | int(5)                                   |          |           |          | -                                                                                |
| `comObjAppObj`                                               | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjAppRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjVisRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjRdvObj`                                               | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjRdvRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjRESObj`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjRESRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjANIMObj`                                              | int(5)                                   |          |           |          | -                                                                                |
| `comObjANIMRes`                                              | int(5)                                   |          |           |          | -                                                                                |
| `comObjCreaObj`                                              | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjCreaRes`                                              | int(5)                                   |          |           |          | -                                                                                |
| `comObjMerchObj`                                             | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjMerchRes`                                             | int(5)                                   |          |           |          | -                                                                                |
| `comObjGmsObj`                                               | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjGmsRes`                                               | int(5)                                   |          |           |          | -                                                                                |
| `comObjACObj`                                                | int(5)                                   |          |           |          | -                                                                                |
| `comObjectifsVentesMagObj`                                   | int(5)                                   |          |           |          | -                                                                                |
| `comObjectifsVentesMagRes`                                   | int(5)                                   |          |           |          | -                                                                                |
| `comObjVmagPnrObj`                                           | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVmagPnrRes`                                           | int(5)                                   |          |           |          | -                                                                                |
| `comObjVmagPnrdObj`                                          | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVmagPnrdRes`                                          | int(5)                                   |          |           |          | -                                                                                |
| `comObjVprivPnrObj`                                          | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVprivPnrRes`                                          | int(5)                                   |          |           |          | -                                                                                |
| `comObjVprivPnrdObj`                                         | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVprivPnrdRes`                                         | int(5)                                   |          |           |          | -                                                                                |
| `comObjVdivCAObj`                                            | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVdivCARes`                                            | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjectifsVentesPrivObj`                                  | int(5)                                   |          |           |          | -                                                                                |
| `comObjectifsVentesPrivRes`                                  | int(5)                                   |          |           |          | -                                                                                |
| `comObjectifsVentesDivObj`                                   | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjectifsVentesDivRes`                                   | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVprivCPObj`                                           | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVprivCPRes`                                           | int(5)                                   |          |           |          | -                                                                                |
| `comObjVmagCPObj`                                            | int(5)                                   |          | yes       |          | -                                                                                |
| `comObjVmagCPRes`                                            | int(5)                                   |          |           |          | -                                                                                |

### Lists

* `COMUOMOIS`
    - `1` Janvier
    - `2` Février
    - `3` Mars
    - `4` Avril
    - `5` Mai
    - `6` Juin
    - `7` Juillet
    - `8` Août
    - `9` Septembre
    - `10` Octobre
    - `11` Novembre
    - `12` Décembre
* `COMOBJYEAR`
    - `2018` 2018
    - `2019` 2019
    - `2020` 2020
    - `2021` 2021
    - `2022` 2022

`ComUserZone` business object definition
----------------------------------------

Zone d'un utilisateur

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comUserZoneId`                                              | int(2)                                   | yes*     | yes       |          | -                                                                                |
| `comUserZoneLibelle`                                         | char(5)                                  |          | yes       |          | -                                                                                |

`ComVentePriv` business object definition
-----------------------------------------

Vente privilège associée à une action commerciale

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `comVPid`                                                    | text(5)                                  | yes*     | yes       |          | Identifiant unique de la vente privilège                                         |

`ComACAppelExt` external object definition
------------------------------------------

AC de type Appel


`ComACMerchandising` external object definition
-----------------------------------------------

AC de type Merchandising


`ComACNRExt` external object definition
---------------------------------------

AC de type Pack NR


`ComACOuverturePVExt` external object definition
------------------------------------------------

AC de type Ouverture Point de vente


`ComACPBRExt` external object definition
----------------------------------------

AC de type Pack Brocante


`ComACPGMExt` external object definition
----------------------------------------

AC de type Pack GMS


`ComACRDVExt` external object definition
----------------------------------------

AC de type RDV


`ComACVAUExt` external object definition
----------------------------------------

AC de type Vente Autre


`ComACVisiteExt` external object definition
-------------------------------------------

AC de type Visite


`ComACVPIExt` external object definition
----------------------------------------

AC de type Vente Privilège


`ComBCPbr` external object definition
-------------------------------------

Bon de commande pack brocante


`ComBCVpriv` external object definition
---------------------------------------

Bon de commande vente privée


`ComConventionDiffExt` external object definition
-------------------------------------------------




`COMConventionOPV` external object definition
---------------------------------------------

convention OPV


`COMFicheClient` external object definition
-------------------------------------------

fiche client


`ComHomeUserInfo` external object definition
--------------------------------------------

Zone de la page d'accueil affichant les infos du user connecté


`ComHomeZoneInfo` external object definition
--------------------------------------------

Zone de la page d'accueil affichant la zone du user connecté


`ComPCClientExt` external object definition
-------------------------------------------

Entrée de menu filtrée sur les clients


`ComPCProspectsExt` external object definition
----------------------------------------------

Entrée de menu filtrée sur les prospects


`ComPRNAccordPartenariat` external object definition
----------------------------------------------------




