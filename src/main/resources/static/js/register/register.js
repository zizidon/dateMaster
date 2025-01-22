document.addEventListener('DOMContentLoaded', function() {
	// トースト通知コンテナの作成
	const toastContainer = document.createElement('div');
	toastContainer.className = 'toast-container';
	document.body.appendChild(toastContainer);

	// アクティブなトーストを追跡
	let activeToast = null;

	// トースト通知を表示する関数
	function showToast(message, type = 'error') {
		// 既存のトーストがあれば削除
		if (activeToast) {
			activeToast.remove();
		}

		const toast = document.createElement('div');
		toast.className = `toast ${type}`;

		let icon = '⚠️';
		if (type === 'success') icon = '✅';
		else if (type === 'warning') icon = '⚠️';
		else if (type === 'error') icon = '❌';

		toast.innerHTML = `
            <span class="toast-icon">${icon}</span>
            <div class="toast-content">${message}</div>
        `;

		toastContainer.appendChild(toast);
		activeToast = toast;

		requestAnimationFrame(() => toast.classList.add('show'));

		setTimeout(() => {
			if (toast.isConnected) {
				toast.classList.remove('show');
				setTimeout(() => toast.remove(), 300);
				activeToast = null;
			}
		}, 3000);
	}

	// パスワード要件の定義
	const requirements = [
		{ id: 'length', text: '8文字以上', regex: /.{8,}/ },
		{ id: 'uppercase', text: '大文字を含む', regex: /[A-Z]/ },
		{ id: 'lowercase', text: '小文字を含む', regex: /[a-z]/ },
		{ id: 'number', text: '数字を含む', regex: /[0-9]/ }
	];

	// パスワード要件リストの作成と配置
	const passwordInput = document.getElementById('password');
	const requirementsList = document.createElement('div');
	requirementsList.className = 'password-requirements';

	requirements.forEach(req => {
		const item = document.createElement('div');
		item.className = 'requirement-item';
		item.id = `req-${req.id}`;
		item.innerHTML = `
            <span class="requirement-icon">○</span>
            <span class="requirement-text">${req.text}</span>
        `;
		requirementsList.appendChild(item);
	});

	passwordInput.parentNode.appendChild(requirementsList);

	// パスワードの入力チェック
	passwordInput.addEventListener('input', function() {
		const password = this.value;
		let validCount = 0;

		requirements.forEach(req => {
			const item = document.getElementById(`req-${req.id}`);
			const isValid = req.regex.test(password);
			item.classList.toggle('valid', isValid);
			item.querySelector('.requirement-icon').textContent = isValid ? '✓' : '○';
			if (isValid) validCount++;
		});
	});

	// フォームのバリデーション
	const form = document.querySelector('form');
	const nameInput = document.getElementById('name');
	const confirmPasswordInput = document.getElementById('confirmPassword');

	form.addEventListener('submit', function(event) {
		event.preventDefault();

		const name = nameInput.value.trim();
		const password = passwordInput.value;
		const confirmPassword = confirmPasswordInput.value;

		// 名前のバリデーション
		if (name.length < 1) {
			showToast('名前を入力してください', 'error');
			nameInput.focus();
			return;
		}

		// パスワード要件のチェック
		let isValid = requirements.every(req => req.regex.test(password));
		if (!isValid) {
			showToast('パスワードの要件を満たしていません', 'warning');
			passwordInput.focus();
			return;
		}

		// パスワード一致チェック
		if (password !== confirmPassword) {
			showToast('パスワードが一致しません', 'error');
			confirmPasswordInput.focus();
			return;
		}

		showToast('登録を実行します', 'success');
		setTimeout(() => form.submit(), 1000);
	});
});