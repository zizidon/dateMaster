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

	// 問題コンテナのホバーエフェクト
	const questionContainers = document.querySelectorAll('main > div');
	questionContainers.forEach(container => {
		container.addEventListener('mouseenter', () => {
			container.style.transform = 'translateY(-5px)';
		});
		container.addEventListener('mouseleave', () => {
			container.style.transform = 'translateY(0)';
		});
	});

	// 削除ボタンの確認ダイアログ
	const deleteForms = document.querySelectorAll('form[action="/dateMaster/deleteProblem"]');
	deleteForms.forEach(form => {
		form.addEventListener('submit', (e) => {
			if (!confirm('この問題を削除してもよろしいですか？')) {
				e.preventDefault();
			}
		});
	});
});