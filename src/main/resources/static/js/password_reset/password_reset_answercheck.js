document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form');
	const userIdInput = document.getElementById('userId');
	const questionSelect = document.getElementById('securityQuestion');
	const answerInput = document.getElementById('answer');

	// フォームバリデーション
	function validateForm() {
		let isValid = true;
		let errorMessage = '';

		// ユーザーID検証
		if (!userIdInput.value.trim()) {
			errorMessage = 'ユーザーIDを入力してください';
			isValid = false;
		}

		// 秘密の質問検証
		if (!questionSelect.value) {
			errorMessage = '秘密の質問を選択してください';
			isValid = false;
		}

		// 回答検証
		if (!answerInput.value.trim()) {
			errorMessage = '回答を入力してください';
			isValid = false;
		}

		if (!isValid) {
			showError(errorMessage);
		}

		return isValid;
	}

	// エラーメッセージ表示
	function showError(message) {
		// 既存のエラーメッセージを削除
		removeError();

		const errorElement = document.createElement('p');
		errorElement.className = 'error-message';
		errorElement.textContent = message;

		// フォームの先頭に挿入
		form.insertBefore(errorElement, form.firstChild);
	}

	// エラーメッセージ削除
	function removeError() {
		const existingError = document.querySelector('.error-message');
		if (existingError) {
			existingError.remove();
		}
	}

	// フォーム送信時のバリデーション
	form.addEventListener('submit', function(e) {
		if (!validateForm()) {
			e.preventDefault();
		}
	});

	// 入力時にエラーメッセージを消す
	[userIdInput, questionSelect, answerInput].forEach(element => {
		element.addEventListener('input', removeError);
	});

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
});