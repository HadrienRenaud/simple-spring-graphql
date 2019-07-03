const dpath = '/srv/simple-spring-graphql';
const clientPath = dpath + '/current/client';
const backPath = dpath + '/current';
const yarn = "/home/ubuntu/.npm-global/bin/yarn";

const utils = require('shipit-utils');

module.exports = shipit => {
    // Load shipit-deploy tasks
    require('shipit-deploy')(shipit);

    shipit.initConfig({
        default: {
            deployTo: dpath,
            repositoryUrl: 'https://github.com/HadrienRenaud/simple-spring-graphql.git',
            servers: 'ubuntu@aws',
        },
    });

    utils.registerTask(shipit, 'build', ['build:client', 'build:back', 'build:finish']);

    shipit.blTask('build:finish', async () => {
        shipit.emit("built");
    });

    shipit.blTask('build:client', async () => {
        await shipit.remote(`cd ${clientPath} && npm i`);
        await shipit.remote(`cd ${clientPath} && ${yarn} build`);
        await shipit.remote(`mv ${clientPath}/build ${backPath}/static`);
    });

    shipit.blTask('build:back', async () => {
        await shipit.remote(`cd ${backPath} && mvn package`);
    });

    shipit.task('start-server', async () => {
        await shipit.remote(`sudo systemctl restart spring-boot-runner.service`);
    });

    shipit.on('built', () => shipit.emit("start-server"));

};
