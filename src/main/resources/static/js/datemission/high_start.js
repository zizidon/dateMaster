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

    // 送信ボタンのアニメーションと検証
    const submitButton = document.querySelector('.submit-button');
    if (submitButton) {
        submitButton.addEventListener('mouseenter', function() {
            this.style.transform = 'scale(1.02)';
        });

        submitButton.addEventListener('mouseleave', function() {
            this.style.transform = 'scale(1)';
        });

        const missionForm = document.getElementById('missionForm');
        missionForm.addEventListener('submit', function(event) {
            const radioGroups = document.querySelectorAll('.radio-group');
            
            // すべてのミッションで選択が行われているか確認
            const allMissionsSelected = Array.from(radioGroups).every(group => {
                return group.querySelector('input:checked');
            });

            if (!allMissionsSelected) {
                event.preventDefault();
                alert('すべてのミッションの達成状況を選択してください。');
            }
        });
    }

    // ページ読み込み時のアニメーション
    const missionContainer = document.querySelector('.mission-container');
    if (missionContainer) {
        missionContainer.style.opacity = '0';
        missionContainer.style.transform = 'translateY(20px)';

        setTimeout(() => {
            missionContainer.style.transition = 'all 0.5s ease';
            missionContainer.style.opacity = '1';
            missionContainer.style.transform = 'translateY(0)';
        }, 100);
    }
});