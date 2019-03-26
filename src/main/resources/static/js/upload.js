(() => {

  const form = document.querySelector('#form')
  const submitButton = document.querySelector('#submit')
  const fileInput = document.querySelector('#file')
  const nameInput = document.querySelector('#title')
  const alertBox = document.querySelector('#alert')

  function hideAlert (seconds) {
    setTimeout(() => {
      alertBox.style.visibility = 'hidden'
    }, seconds * 1000)
  }

  function notifyError (msg) {
    alertBox.style.visibility = 'visible'
    alertBox.className = 'alert alert-danger'
    alertBox.innerHTML = msg
    form.reset()
    hideAlert(5)
  }

  function notifySuccess (msg) {
    alertBox.style.visibility = 'visible'
    alertBox.className = 'alert alert-success'
    alertBox.innerHTML = msg
    form.reset()
    hideAlert(10)
  }

  submitButton.addEventListener('click', async () => {
    const file = fileInput.files[0]
    const form = new FormData()
    form.append('image', file)
    form.append('name', nameInput.value)
    try {
      const response = await axios({
        method: 'POST',
        url: '/upload',
        data: form,
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      })
      const uuid = `${response.data.uuid}`
      notifySuccess(`
            Image uploaded !! <a href="/images/${uuid}" target="_blank">View my image</a></br>
            Your uuid: <pre><code>${uuid}</code></pre>
         `)
    } catch (e) {
      notifyError(e)
    }
  })

})()
