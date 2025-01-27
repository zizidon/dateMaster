document.addEventListener('DOMContentLoaded', function() {
    const hamburgerButton = document.querySelector('.hamburger-button');
    const hamburgerMenu = document.querySelector('.hamburger-menu');

    // オーバーレイ要素を作成
    const overlay = document.createElement('div');
    overlay.className = 'overlay';
    document.body.appendChild(overlay);

    function toggleMenu() {
        hamburgerButton.classList.toggle('active');
        hamburgerMenu.classList.toggle('active');
        overlay.classList.toggle('active');

        // メニューが開いているときはスクロールを無効化
        if (hamburgerMenu.classList.contains('active')) {
            document.body.style.overflow = 'hidden';
        } else {
            document.body.style.overflow = '';
        }
    }

    hamburgerButton.addEventListener('click', toggleMenu);
    overlay.addEventListener('click', toggleMenu);

    // メニューリンクをクリックしたときにメニューを閉じる
    const menuLinks = document.querySelectorAll('.hamburger-menu a');
    menuLinks.forEach(link => {
        link.addEventListener('click', () => {
            toggleMenu();
        });
    });

    // ミッションアイテムのアニメーション
    const missionItems = document.querySelectorAll('.mission-item');
    missionItems.forEach((item, index) => {
        item.style.opacity = '0';
        item.style.transform = 'translateY(20px)';

        setTimeout(() => {
            item.style.transition = 'all 0.5s ease';
            item.style.opacity = '1';
            item.style.transform = 'translateY(0)';
        }, 100 * (index + 1));
    });

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

    // フォーム送信時の確認
    const missionForm = document.getElementById('missionForm');
    if (missionForm) {
        missionForm.addEventListener('submit', function(e) {
            const confirmed = confirm('デートを終了してよろしいですか？');
            if (!confirmed) {
                e.preventDefault();
            }
        });
    }

    // ラジオボタンの選択時のエフェクト
    const radioButtons = document.querySelectorAll('input[type="radio"]');
    radioButtons.forEach(radio => {
        radio.addEventListener('change', function() {
            // タップ時の振動効果
            if (window.navigator.vibrate) {
                window.navigator.vibrate(50);
            }

            // 選択時のアニメーション
            const missionItem = this.closest('.mission-item');
            missionItem.style.transform = 'scale(1.02)';
            setTimeout(() => {
                missionItem.style.transform = 'scale(1)';
            }, 200);
        });
    });
});