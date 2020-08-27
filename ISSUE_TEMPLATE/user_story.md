---
name: User Story
about: Describe the User Story
title: '[US]'
labels: US
assignees: ''

---
>Choisissez entre Français et Anglais pour décrire l'issue et tenez-vous à une seule langue. 
>N'hésitez pas à adapter le patron des issues en fonction de vos choix. 

**Titre/Title :** En quelques mots, résumez l'objectif de l'US par exemple _Ajout d'un produit dans le panier_.


**Description :**

**As a** [type of user], **I want** [an action] **so that** [a benefit/a value]<br>
**En tant que** [type of user], **Je veux** [an action] **afin de** [a benefit/a value]


**Priorité/Priority :** C'est la priorité dans les attentes du client, ce qui est important c'est que toute l'équipe soit d'accord sur l'échelle, vous pouvez aussi faire le choix d'étoiles, de chiffres ou suivre l'échelle donnée ci-dessous. Mettez à jour le patron quand l'équipe a fait un choix.

Proposition d'échelle, la [méthode MoSCoW](https://paper-leaf.com/insights/prioritize-user-stories/)
  1. _**Must have/Doit avoir :** la première version de ce produit nécessite absolument cette fonctionnalité - elle est essentielle au succès du produit._
  2. _**Should Have/Devrait avoir** : l'idéal serait que la première version de ce produit dispose de cette fonctionnalité, mais elle n'est pas absolument nécessaire. Elles peuvent être aussi importantes, mais pas aussi critiques en termes de temps, que les "Must Have"._
  3. _**Could have/Aurait pu**: l'histoire de l'utilisateur a de la valeur et est souhaitable, mais en fin de compte, elle n'est pas nécessaire._
  4. _**Won't have/N'aura pas** : l'histoire de l'utilisateur est considérée comme étant parmi les moins critiques ou les moins utiles._
  
  
**Estimation/Estimate :** Préciser l'effort requis pour mettre en œuvre la US.
Vous pouvez par exemple faire le choix de l'évaluer en homme/heure. 


**Règle métier /Business rules :**
Précisez ici les règles métiers essentielles pour le développement de cette user-story. 

Ces règles sont écrites en français ou en anglais avec comme objectif de très facilement comprendre le travail à effectuer. 


_Voici un exemple ci-dessous sur un panier d’un site e-commerce :_
```
lorsque je rajoute un élément supplémentaire d'un produit dans mon 
panier.
    - si quantité > stock alors erreur "pas assez de stock 
      disponible"
    - si quantité < stock alors on ajoute +1 à la quantité_
```
  
**Critère d'acceptation/Acceptance criteria**
Précisez l'ensemble des conditions que la story doit satisfaire pour être considérée comme complète et terminée.

Plus spécifiquement décrivez un ensemble de scénario qui deviendront des tests d'acceptation. Précisez bien les données associées comme vous le voyez dans les scénarios d'exemples données ci-dessous. 

**Given** some context<br>
**When** some action is carried out<br>
**Then** a set of observable outcomes should occur <br>
  
_Voici quelques exemples_<br>
_**Scénario:**_
```
     Etant donné que je suis sur mon panier 
     Et que j'ai un produit d'id "1234" en quantité "1"
     Et que le stock restant sur ce produit est de "0"
     Quand j'ajoute "1" quantité sur mon produit
     Alors mon panier affichera une erreur
```

_**Scénario:**_<br>
```
    Etant donné que je suis sur mon panier 
    Et que j'ai un produit d'id "1235" en quantité "1" <br>
    Et que le stock restant sur ce produit est de "10"
    Quand j'ajoute "1" quantité de mon produit
    Alors mon produit aura "2" quantités
```

> Ce texte et ces exemples s'inspirent de  : https://blog.myagilepartner.fr/index.php/2017/03/18/story-a4/
