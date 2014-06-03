var document = search.findNode(args.nodeRef);
var assoc = document.childAssocs["tecno:adjuntos"][0]
var link = assoc.storeType + "://" + assoc.storeId + "/" + assoc.id

model.contentNode = search.findNode(link);
model.attach = true;
model.attachFileName = 'lala.pdf';

logger.log("------> link:" + link);