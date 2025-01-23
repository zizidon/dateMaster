document.addEventListener('DOMContentLoaded', function() {
	// 完了メッセージコンテナのアニメーション
	const completionContainer = document.querySelector('.completion-container');

	// checkmarkアイコンの追加
	const completionIcon = document.createElement('div');
	completionIcon.className = 'completion-icon';
	completionIcon.textContent = '✓';

	// メッセージ要素の取得とラップ
	const originalMessage = document.querySelector('main p:first-child');
	const messageWrapper = document.createElement('div');
	messageWrapper.className = 'completion-message';

	// メッセージを少し悲しげに変更
	messageWrapper.innerHTML = `アカウントを削除しました。<br>またのご利用をお待ちしております。`;

	// ボタン要素の取得
	const loginLink = document.querySelector('main a');

	// アイコンとメッセージを追加するコンテナの準備
	const container = document.createElement('div');
	container.className = 'completion-container';

	// コンテナに要素を追加
	container.appendChild(completionIcon);
	container.appendChild(messageWrapper);
	container.appendChild(loginLink);

	// メインコンテンツを置き換え
	const mainContent = document.querySelector('main');
	mainContent.innerHTML = '';
	mainContent.appendChild(container);

	// アニメーションの実行
	setTimeout(() => {
		container.classList.add('visible');
	}, 100);
});