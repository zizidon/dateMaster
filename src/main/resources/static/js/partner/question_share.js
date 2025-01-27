document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御
	const hamburgerButton = document.querySelector('.hamburger-button');
	const hamburgerMenu = document.querySelector('.hamburger-menu');
	const overlay = document.createElement('div');
	overlay.className = 'overlay';
	document.body.appendChild(overlay);

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

	// ホバーエフェクトを問題表示エリアに追加
	const questionContainer = document.querySelector('main > div:first-of-type');
	questionContainer.addEventListener('mouseenter', () => {
		questionContainer.style.transform = 'translateY(-5px)';
	});
	questionContainer.addEventListener('mouseleave', () => {
		questionContainer.style.transform = 'translateY(0)';
	});
});