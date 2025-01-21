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

	// 完了メッセージのアニメーション
	const completeMessage = document.querySelector('.complete-message');
	completeMessage.style.opacity = '0';
	completeMessage.style.transform = 'translateY(20px)';

	// ページ読み込み後にアニメーションを実行
	setTimeout(() => {
		completeMessage.style.transition = 'all 0.5s ease-out';
		completeMessage.style.opacity = '1';
		completeMessage.style.transform = 'translateY(0)';
	}, 100);
});