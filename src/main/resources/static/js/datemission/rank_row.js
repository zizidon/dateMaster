document.addEventListener('DOMContentLoaded', function() {
    // 戻るボタンのアニメーション
    const backButton = document.querySelector('.back-button button');
    if (backButton) {
        backButton.addEventListener('mouseenter', function() {
            this.style.transform = 'translateX(-5px)';
        });

        backButton.addEventListener('mouseleave', function() {
            this.style.transform = 'translateX(0)';
        });
    }

    // ミッションリストのアニメーション
    const missions = document.querySelectorAll('ul li');
    missions.forEach((mission, index) => {
        mission.style.opacity = '0';
        mission.style.transform = 'translateY(20px)';

        setTimeout(() => {
            mission.style.transition = 'all 0.5s ease';
            mission.style.opacity = '1';
            mission.style.transform = 'translateY(0)';
        }, 100 * (index + 1));
    });

    // ボタンのインタラクティブ効果
    const buttons = document.querySelectorAll('form button');
    buttons.forEach(button => {
        // タップまたはクリック時の振動効果（サポートされている場合）
        button.addEventListener('touchstart', function() {
            if (window.navigator.vibrate) {
                window.navigator.vibrate(50);
            }
        });

        // タッチとマウスのホバーエフェクト
        button.addEventListener('mouseenter', function() {
            this.style.transform = 'scale(1.02)';
        });

        button.addEventListener('mouseleave', function() {
            this.style.transform = 'scale(1)';
        });

        // クリック時の色のフィードバック（フォーム送信を妨げない）
        button.addEventListener('click', function() {
            const originalColor = this.style.backgroundColor;
            this.style.backgroundColor = this.classList.contains('update-btn') ? '#2c70a3' : '#67a339';
            
            setTimeout(() => {
                this.style.backgroundColor = originalColor;
            }, 300);
        });
    });
});