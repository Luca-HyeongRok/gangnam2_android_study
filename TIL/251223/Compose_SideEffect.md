# Compose - SideEffect

## SideEffect 기초
- 컴포저블은 side-effect(부작용)이 없어야 한다. 하지만 앱의 상태를 변경해야 하는 경우, 컴포저블의 수명 주기를 인지하는 제어된 환경에서 변경하는 행위를 호출해야 한다.

즉, Side-effects는 컴포저블 스코프 외부에서 발생하는 앱 상태의 변경이다.
 
# 8가지 EffectAPI
- Effect는 UI를 리턴하지 않고 composition이 완료될 때 부작용이 실행되도록 하는 Composable 함수.
- 반응형 UI는 본질적으로 비동기식이며, Jetpack Compose는 API 수준에서 코루틴을 사용하여 이 문제를 해결한다.

```file
Effect API란?

Effect는 UI를 그리지 않고, Composition 및 Composable 생명주기에 맞춰 부작용을 안전하게 실행하기 위한 Composable 함수이다.

Compose의 반응형 UI 특성상 비동기 작업이 빈번하며, 이를 위해 Effect API는 코루틴 기반으로 설계되어 있다.
```
1. LaunchedEffect : Composable 스코프에서 비동기 코드 실행
2. rememberCoroutineScope : 컴포저블 외부에서 코루틴을 실행
3. rememberUpdatedState : 변경되는 값을 참조할 때 사용
4. DisposableEffect : 정리(cleanup)가 필요한 작업에 사용
5. SideEffect : 컴포저블 내에서 일반 동기 코드 매번 실행
6. produceState : 외부 데이터를 State로 변환할 때
7. derivedStateOf : 다른 상태를 기반으로 새로운 상태를 계산할 때 사용
8. snapshotFlow: Compose의 상태를 Flow로 변환


