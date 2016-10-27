# View Animation

Example of View Animations with ObjectAnimator.

## About

Example using a `translate`, `scale` and a `rotation3d`. Also can use an `alpha` animation. The animation has a listener for when finishes.
After animation is applied, the view is fully functional.

Press the toolbar icon to see the animation.

## Configuration

```java
AnimationManager animationManager = new AnimationManager();
animationManager.setWidth(MetricsUtils.get().getWidth())
                .setPositionPercentage(0.5f)
                .setScaleFactor(0.8f)
                .setAlpha(0.5f)
                .setRotate3d(true);
```

## Usage

- Without feedback

    ```java
    animationManager.animateView(view);
    ```

- With feedback

    ```java
    animationManager.animateView(view, () -> Log.i(Constants.TAG, "Animation finished"));
    ```

## Screenshots

<img src="demo.gif" width="350"/>

## License
    Copyright 2016 Esteban Latre
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
