# Cosmetic Recommender System (Test)
해당 프로젝트는 미국의 비영리 단체인 EWG에서 제공하는 화장품의 성분에 대한 유해도와 화장품을 사용해본 사람들의 상품평을 이용하여
퍼지추론을 통해 해당 시스템을 이용하는 유저에게 특정 제품의 추천도를 알려주는 프로젝트의 테스트 버전입니다.

## 해당 프로젝트의 환경은 다음과 같습니다.
- Windows 7 Ultimate
- Eclipse Mars
- MySQL Workbench 6.3.10
- Jericho HTML Parser 3.4
- KOMORAN 형태소 분석기 2.0

## 해당 프로젝트의 소스는 다음과 같이 구성되어 있습니다.
- BasicParser.java : EWG에서 제공하는 화장품의 성분과 유해도를 파싱해 성분 점수를 계산한 뒤 DB에 저장하는 역할을 합니다.
- CollectMorpheme.java : 특정 제품군(여기서는 화장품)의 상품평을 수집합니다. 수집한 상품평은 형태소 단위로 분류한 후, 감성사전을 만드는 것에 이용합니다.
- PolarityClassfication.java : 특정 제품을 입력하면 감성사전을 통해 해당 제품의 상품평을 분석한 뒤 상품평 점수를 계산합니다.
- Inference.java : 계산된 성분 점수와 상품평 점수를 이용하여 퍼지 추론을 합니다.