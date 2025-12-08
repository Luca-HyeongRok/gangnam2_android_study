# Modifier (수정자)
: Modifier는 Jetpack Compose에서 UI 요소의 크기, 동작, 모양 등을 수정하고 꾸미는 데 사용된다

- UI 요소의 크기, 패딩, 마진 설정
- 배경색, 테두리 등 시각적 스타일링
- 클릭, 스크롤 등의 사용자 상호작용 처리
- 요소의 배치와 정렬 방식 지정

## 기본 사용 방법
```kotlin
Text(
    text = "Hello World",
    modifier = Modifier
        .padding(16.dp) // 패딩추가
        .background(Color.Gray) // 배경색 설정
        .clickable{} // 클릭 이벤트 추가
)
```

## 특징
- 체이닝 방식: 여러 Modifier를 점(.)으로 연결하여 사용
- 순서 중요성: 적용되는 순서에 따라 다른 결과가 나타남
- 재사용 가능: 변수나 함수로 만들어 재사용 가능
- 확장 가능: 커스텀 Modifier 생성 가능

## 주요 Modifier
- 크기: size, width, height, fillMaxSize
- 여백: padding, offset
- 스타일: background, border, alpha
- 상호작용: clickable, scrollable
- 레이아웃: weight, align

## 순서의 중요성
- 배경색 -> 패딩 : 노란 배경이 패딩 바깥까지
- 패딩 -> 배경색 : 노란 배경이 패딩 안쪽까지

## 여백, Window Inset
: Compose에서 여백을 잘 쓰려면 padding을 활용하고, 시스템 UI(WindowInsets)를 고려하며, 반응형 spacing 을 유연하게 조절해야 한다.

- padding vs margin(spacer)의 개념 : 컴포넌트 내부냐 외부냐에 따라 설정하기 
- WindowInsets 활용 : 기기 화면에서 시스템 UI가 차지하는 영역
- 시스템 UI 고려한 spacing : 상태바, 키보드 등을 고려하기 
-  동적 spacing 처리 방법 : 기기마다 화면 크기, 비율 등이 다름. 그래서 고정보다는 반응형

## 모양 관련
- 둥근 이미지
```css
1. 원본 이미지 로드  

2. ContentScale.Crop 으로 정사각형/지정된 영역에 맞게 축소/확대 + 넘치는 부분 잘림  

3. clip(CircleShape)으로 동그랗게 마스킹 처리
```
- 모서리 굴리기
```css
1. 원본 이미지 로드  

2. ContentScale.Crop 으로 지정된 영역에 맞게 축소/확대 + 넘치는 부분 잘림  

3. clip(RoundedCornerShape(topStartPercent = 50))으로  
   왼쪽 위 모서리(topStart)를 50%만큼 둥글게 잘라내어  
   카드/이미지의 특정 모서리만 라운드 처리
```

## offset 과 padding의 차이
- Padding: 내부 공간을 확보해 배치 자체에 영향을 주는 여백
- Offset: 기존 위치는 유지한 채 보이는 위치만 이동

## 유저 인터렉션
: 사용자가 화면 요소를 직접 조작할 때 발생하는 모든 상호작용

### 1. 일반 클릭
: 터치(버튼, 이미지 등) 이벤트가 발생하면 콜백 실행

```kotlin
Text(
"Click Me",
modifier = Modifier.clickable {}
)
```
### 2. Press / Hover 처리
- Press : 손가락이나 마우스로 누르고 있는 상태
- Hover : 마우스가 위로 올려졌을 때
```kotlin
val interaction = remember { MutableInteractionSource() }
val pressed by interaction.collectIsPressedAsState()

Box(
    Modifier.clickable(interactionSource = interaction, indication = null) {}
) {
    Text(if (pressed) "누르는 중..." else "클릭 준비")
}
```
### 3. Focus 처리
: TextField / 검색창 / 폼 입력 시 사용

```kotlin
val focusManager = LocalFocusManager.current

TextField(
value = text,
onValueChange = { text = it },
modifier = Modifier.focusable(),
keyboardActions = KeyboardActions(
onDone = { focusManager.clearFocus() }
)
)

```
- 키보드 표시/숨기기 제어
- 입력 필드 간 이동
- UX 향상 (자동 포커스 이동)

### 4. TextField 입력 처리
: 텍스트 상태를 기억하고 UI에 반영

```kotlin
var name by remember { mutableStateOf("") }

TextField(
    value = name,
    onValueChange = { name = it },
    label = { Text("Name") }
)

```
- 입력값이 바뀔 때마다 UI 자동 업데이트

- 이외에도 Keyboard액션, 스크롤 제스쳐 등이 있다.