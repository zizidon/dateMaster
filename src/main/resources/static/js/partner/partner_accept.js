document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御
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

	// 戻るボタンの確認ダイアログ
	const backButton = document.querySelector('.back-button button');
	if (backButton) {
		backButton.addEventListener('click', function(e) {
			const unsavedChanges = false; // 変更が保存されていない状態を管理する変数
			if (unsavedChanges) {
				const confirmed = confirm('変更が保存されていません。本当に戻りますか？');
				if (!confirmed) {
					e.preventDefault();
				}
			}
		});
	}

	// 拒否・承認ボタンの確認ダイアログ
	const actionButtons = document.querySelectorAll('.reject-button, .accept-button');
	actionButtons.forEach(button => {
		button.addEventListener('click', function(e) {
			const action = this.textContent;
			const confirmed = confirm(`このパートナー申請を${action}しますか？`);
			if (!confirmed) {
				e.preventDefault();
			}
		});
	});

	// ハート降下アニメーション
	function createFallingHearts() {
		const body = document.body;
		setInterval(() => {
			const heart = document.createElement('div');
			heart.classList.add('heart');
			heart.style.left = `${Math.random() * 100}%`;
			heart.style.animationDuration = `${5 + Math.random() * 5}s`;
			heart.style.opacity = Math.random();
			body.appendChild(heart);

			// 一定時間後に削除
			setTimeout(() => {
				heart.remove();
			}, 5000);
		}, 300);
	}
	createFallingHearts();
});