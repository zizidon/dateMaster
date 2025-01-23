document.addEventListener('DOMContentLoaded', function() {
    // ボタンにホバーエフェクトと音声効果を追加（オプション）
    const buttons = document.querySelectorAll('.button-group button');
    
    buttons.forEach(button => {
        // タップまたはクリック時の振動効果（サポートされている場合）
        button.addEventListener('touchstart', function() {
            if (window.navigator.vibrate) {
                window.navigator.vibrate(50);
            }
        });

        // ボタンにホバーエフェクト（CSSで既に実装されているが、必要に応じて追加のJavaScript効果を入れられます）
        button.addEventListener('mouseenter', function() {
            this.style.transform = 'scale(1.02)';
        });

        button.addEventListener('mouseleave', function() {
            this.style.transform = 'scale(1)';
        });
    });

    // オプション：ページ読み込み時のアニメーション
    const buttonGroup = document.querySelector('.button-group');
    if (buttonGroup) {
        buttonGroup.style.opacity = '0';
        buttonGroup.style.transform = 'translateY(20px)';

        setTimeout(() => {
            buttonGroup.style.transition = 'all 0.5s ease';
            buttonGroup.style.opacity = '1';
            buttonGroup.style.transform = 'translateY(0)';
        }, 100);
    }

    // 戻るボタンにアニメーション
    const backButton = document.querySelector('.back-button button');
    if (backButton) {
        backButton.addEventListener('mouseenter', function() {
            this.style.transform = 'translateX(-5px)';
        });

        backButton.addEventListener('mouseleave', function() {
            this.style.transform = 'translateX(0)';
        });
    }
});