document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューの制御（partner_request.jsと同様）
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

	const menuLinks = document.querySelectorAll('.hamburger-menu a');
	menuLinks.forEach(link => {
		link.addEventListener('click', () => {
			toggleMenu();
		});
	});

	// 削除ボタンに追加の確認ダイアログ
	const deleteForm = document.querySelector('form[name="form1"]');
	const deleteButton = deleteForm.querySelector('button[type="submit"]');

	deleteButton.addEventListener('click', function(e) {
		const confirmed = confirm('パートナーを完全に削除します。この操作は取り消せません。本当によろしいですか？');
		if (!confirmed) {
			e.preventDefault();
		}
	});

	// 戻るボタンの確認ダイアログ
	const backButton = document.querySelector('.back-button button');
	backButton.addEventListener('click', function(e) {
		const confirmed = confirm('パートナー削除をキャンセルしてパートナー画面に戻ります。よろしいですか？');
		if (!confirmed) {
			e.preventDefault();
		}
	});
});