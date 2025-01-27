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

    // ミッションアイテムのアニメーション
    const missionItems = document.querySelectorAll('.mission-item');
    missionItems.forEach((item, index) => {
        item.style.opacity = '0';
        item.style.transform = 'translateY(20px)';

        setTimeout(() => {
            item.style.transition = 'all 0.5s ease';
            item.style.opacity = '1';
            item.style.transform = 'translateY(0)';
        }, 100 * index); // 各アイテムを順番に表示
    });

    // フォームの送信処理
    const missionForm = document.getElementById('missionForm');
    if (missionForm) {
        missionForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // フォームの値をチェック
            const radios = document.querySelectorAll('input[type="radio"]:checked');
            if (radios.length !== missionItems.length) {
                alert('すべてのミッションの達成状況を選択してください。');
                return;
            }

            // バイブレーション効果（モバイルデバイスの場合）
            if (window.navigator.vibrate) {
                window.navigator.vibrate(50);
            }

            // フォームを送信
            this.submit();
        });
    }

    // ラジオボタンの選択エフェクト
    const radioInputs = document.querySelectorAll('input[type="radio"]');
    radioInputs.forEach(input => {
        input.addEventListener('change', function() {
            const missionItem = this.closest('.mission-item');
            missionItem.style.transform = 'scale(1.02)';
            setTimeout(() => {
                missionItem.style.transform = 'scale(1)';
            }, 200);
        });
    });
});