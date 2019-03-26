
async function getImages () {
  try {
    const response = await axios.get('/images')
    return response.data
  } catch (err) {
    console.error(err)
  }
}

async function getImage (image) {
  const placeholderGeneratorUrl = `https://via.placeholder.com`
  try {
    const localImageUrl = `/images/${image.uuid}`
    await axios.get(localImageUrl)
    return localImageUrl
  } catch (err) {
    return `${placeholderGeneratorUrl}/${image.imageInfo.scale.width}x${image.imageInfo.scale.height}`
  }
}

async function getImageStore (images) {

  return images.reduce(async (acc, image) => {
    acc = await acc
    if (!(image.groupUuid in acc)) {
      acc[image.groupUuid] = []
    }
    image.data = await getImage(image)
    acc[image.groupUuid].push(image)
    return acc
  }, {})
}

function findGroupInStore (group, imageStore) {
  return imageStore[group]
}

function getCookie (cname) {
  var name = cname + '='
  var decodedCookie = decodeURIComponent(document.cookie)
  var ca = decodedCookie.split(';')
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i]
    while (c.charAt(0) === ' ') {
      c = c.substring(1)
    }
    if (c.indexOf(name) === 0) {
      return c.substring(name.length, c.length)
    }
  }
  return null
}

function orderGroup (group) {
  return group.sort(({ imageInfo: iia }, { imageInfo: iib }) => {
    return iia.scale.width * iia.scale.height - iib.scale.width * iib.scale.height
  })
}

function orderImageStore (imageStore) {
  for (const key in imageStore) {
    if (imageStore.hasOwnProperty(key)) {
      imageStore[key] = orderGroup(imageStore[key])
    }
  }
}

function drawGroup (container, group) {

  const images = group.map(({ data, name, original, imageInfo }) => {
    const caption = original ? 'Original' : `${imageInfo.scale.width}x${imageInfo.scale.height}`

    return `<figure class="figure">
      <img src="${data}" class="rounded float-left" alt="${name}">
      <figcaption class="figure-caption text-center">${caption}</figcaption>
      </figure>
    `
  })
  const imageBlock = document.createElement('div')
  imageBlock.classList.add('image-block')
  const [firstGroup] = group
  imageBlock.innerHTML = `
    <fieldset>
      <legend>${firstGroup.name}</legend>
      ${images.join('')}
    </fieldset>
  `
  container.append(imageBlock)
}

(async () => {

  const imageZone = document.querySelector('#image-container')
  const images = await getImages()
  const imageStore = await getImageStore(images)
  orderImageStore(imageStore)
  const lastUploadedGroupUuid = getCookie('Image-Scaler-Last-Uploaded-Group')
  if (lastUploadedGroupUuid) {
    const lastUploadedGroup = findGroupInStore(lastUploadedGroupUuid, imageStore)
    if (lastUploadedGroup) {
      delete imageStore[lastUploadedGroupUuid]
      drawGroup(imageZone, lastUploadedGroup)
    }
  }
  const groups = Object.values(imageStore)
  for (const group of groups) {
    drawGroup(imageZone, group)
  }
})()
