document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御
	const hamburgerButton = document.querySelector('.hamburger-button');
	const hamburgerMenu = document.querySelector('.hamburger-menu');
	const overlay = document.querySelector('.overlay');

	function toggleMenu() {
		hamburgerButton.classList.toggle('active');
		hamburgerMenu.classList.toggle('active');
		overlay.classList.toggle('active');

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
		link.addEventListener('click', toggleMenu);
	});

	// 確認コンテナのアニメーション
	const confirmationContainer = document.querySelector('.confirmation-container');
	confirmationContainer.style.opacity = '0';
	confirmationContainer.style.transform = 'translateY(20px)';

	// ページ読み込み後にアニメーションを実行
	setTimeout(() => {
		confirmationContainer.style.transition = 'all 0.5s ease-out';
		confirmationContainer.style.opacity = '1';
		confirmationContainer.style.transform = 'translateY(0)';
	}, 100);
});